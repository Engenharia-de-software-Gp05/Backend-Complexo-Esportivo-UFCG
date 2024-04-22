package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service.MailService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.service.SignUpConfirmationCodeService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.*;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserNameEmailDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SignUpConfirmationCode;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.RandomStringGenerator;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
class AuthServiceImpl implements AuthService {

    private static final Long EXPIRATION_TIME_FOR_LOGIN_TOKEN = 120L;
    private static final Long EXPIRATION_TIME_FOR_REGISTER_TOKEN = 15L;
    private static final Long EXPIRATION_TIME_FOR_RECOVER_PASSWORD_TOKEN = 5L;
    private static final int PASSWORD_SIZE = 12;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SaceUserService saceUserService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private SignUpConfirmationCodeService confirmationCodeService;

    private static final Log LOGGER = LogFactory.getLog(AuthServiceImpl.class);

    @Override
    @Transactional
    public AuthTokenDto login(AuthUsernamePasswordDto credentials) {
        SaceUser user = findUserByUsername(credentials.username());

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), credentials.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService
                .generateToken((SaceUser) auth.getPrincipal(), EXPIRATION_TIME_FOR_LOGIN_TOKEN);

        return new AuthTokenDto(token);
    }

    @Override
    @Transactional
    public void recoverPassword(String username) {
        SaceUser user = findUserByUsername(username);

        String token = tokenService
                .generateToken(user, EXPIRATION_TIME_FOR_RECOVER_PASSWORD_TOKEN);
        // TODO
        // fazer constantes pras urls e usar formatter pra criar o link aqui.
        mailService.sendRecoverPasswordLinkEmail(user.getName(), token, user.getEmail());
    }

    public SaceUser findUserByUsername(String username) {
        return saceUserService.findByEmail(username).orElseGet(
                () -> saceUserService.findByStudentId(username).orElseThrow(
                        () -> new SaceResourceNotFoundException(
                                String.format(SaceUserExceptionMessages.USER_WITH_USERNAME_NOT_FOUND, username)
                        )
                )
        );
    }

    @Override
    @Transactional
    public void updatePassword(AuthPasswordUpdateDto passwordUpdateDto) {
        Long requesterId = authenticatedUser.getAuthenticatedUserId();

        String currentPassword = passwordEncoder.encode(passwordUpdateDto.currentPassword());
        String encodedNewPassword = passwordEncoder.encode(passwordUpdateDto.newPassword());

        saceUserService.updatePasswordById(requesterId, currentPassword, encodedNewPassword);
    }


    @Override
    @Transactional
    public void confirmEmailRegistered(String confirmationCode) {
        Long requesterUserId = authenticatedUser.getAuthenticatedUserId();

        checkIfConfirmationCodeIsValid(confirmationCode, requesterUserId);

        saceUserService.updateUserRolesById(Set.of(SaceUserRoleEnum.ROLE_USER), requesterUserId);
    }

    private void checkIfConfirmationCodeIsValid(String confirmationCode, Long requesterUserId) {
        SignUpConfirmationCode signUpConfirmationCode = confirmationCodeService.findByUserIdAndConfirmationCode(requesterUserId, confirmationCode)
                .orElseThrow(
                        () -> new SaceResourceNotFoundException(
                                SaceUserExceptionMessages.CONFIRMATION_CODE_IS_NOT_RELATED_TO_USER_WITH_ID.formatted(confirmationCode, requesterUserId)
                        )
                );

        if (signUpConfirmationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new SaceInvalidArgumentException(
                    SaceUserExceptionMessages.CONFIRMATION_CODE_EXPIRED
            );
        }
    }

    @Override
    @Transactional
    public AuthTokenDto register(AuthRegisterDataWithoutRolesDto credentials) {
        this.checkIfUserExists(credentials.email(), credentials.studentId());

        String encodedPassword = passwordEncoder.encode(credentials.password());
        SaceUser newUser = makeUser(credentials, encodedPassword);
        SaceUser user = saceUserService.save(newUser);

        confirmationCodeService.generate(user.getId());

        String token = tokenService
                .generateToken(user, EXPIRATION_TIME_FOR_REGISTER_TOKEN);

        return new AuthTokenDto(token);
    }

    @Override
    @Transactional
    public SaceUserNameEmailDto registerByAdmin(AuthRegisterDataWithRolesDto credentials) {
        this.checkIfUserExistsByEmail(credentials.email());

        String temporaryPassword = RandomStringGenerator.randomIncludingSpecialCharacters(PASSWORD_SIZE);

        String encodedPassword = passwordEncoder.encode(temporaryPassword);
        SaceUser newUser = makeUserWithRoles(credentials, encodedPassword);
        SaceUser user = saceUserService.save(newUser);

        mailService.sendSignUpTemporaryPasswordEmail(user.getName(), temporaryPassword, user.getEmail());
        return new SaceUserNameEmailDto(user.getEmail(), user.getName());
    }

    private void checkIfUserExists(String email, String studentId) {
        checkIfUserExistsByEmail(email);
        checkIfUserExistsByStudentId(studentId);
    }

    private void checkIfUserExistsByStudentId(String studentId) {
        if (saceUserService.existsByStudentId(studentId)) {
            throw new SaceConflictException(
                    String.format(SaceUserExceptionMessages.USER_WITH_STUDENT_ID_ALREADY_EXISTS, studentId)
            );
        }
    }

    private void checkIfUserExistsByEmail(String email) {
        if (saceUserService.existsByEmail(email)) {
            throw new SaceConflictException(
                    String.format(SaceUserExceptionMessages.USER_WITH_EMAIL_ALREADY_EXISTS, email)
            );
        }
    }

    private SaceUser makeUser(AuthRegisterDataWithoutRolesDto data, String encodedPassword) {
        return new SaceUser(
                data.email(),
                data.name(),
                data.phoneNumber(),
                data.studentId(),
                encodedPassword,
                Set.of(SaceUserRoleEnum.ROLE_PENDING)
        );
    }

    private SaceUser makeUserWithRoles(AuthRegisterDataWithRolesDto data, String encodedPassword) {
        return new SaceUser(
                data.email(),
                data.name(),
                data.phoneNumber(),
                encodedPassword,
                data.userRoles()
        );
    }

}
