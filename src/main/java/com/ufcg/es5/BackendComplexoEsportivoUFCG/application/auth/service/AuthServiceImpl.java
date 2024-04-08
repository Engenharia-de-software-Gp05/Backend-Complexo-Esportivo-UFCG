package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service.MailService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.service.SignUpConfirmationCodeService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithoutRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthUsernamePasswordDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.RandomStringGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class AuthServiceImpl implements AuthService {

    private static final Long EXPIRATION_TIME_FOR_LOGIN_TOKEN = 120L;
    private static final Long EXPIRATION_TIME_FOR_RECOVER_PASSWORD_TOKEN = 5L;

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

    @Override
    @Transactional
    public AuthTokenDto login(AuthUsernamePasswordDto credentials) {
        SaceUser user = findUserByUsername(credentials.username());

        var usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), credentials.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService
                .generateToken((SaceUser) auth.getPrincipal(), EXPIRATION_TIME_FOR_LOGIN_TOKEN);

        return new AuthTokenDto(token);
    }

    @Override
    @Transactional
    public void recoverPassword(String username) {
        SaceUser user = findUserByUsername(username);

        var token = tokenService
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
    public void updatePassword(String newPassword) {
        Long requesterUserId = authenticatedUser.getAuthenticatedUserId();
        SaceUser requesterUser = saceUserService.findById(requesterUserId)
                .orElseThrow(
                        () -> new SaceResourceNotFoundException(String.format(SaceUserExceptionMessages.USER_WITH_ID_NOT_FOUND, requesterUserId))
                );

        String encodedPassword = passwordEncoder.encode(newPassword);
        requesterUser.setPassword(encodedPassword);

        saceUserService.save(requesterUser);
    }

    @Override
    @Transactional
    public void confirmEmailRegistered(String confirmationCode) {
        Long requesterUserId = authenticatedUser.getAuthenticatedUserId();

        if (!confirmationCodeService.existsByUserIdAndConfirmationCode(requesterUserId, confirmationCode)) {
            throw new SaceResourceNotFoundException(
                    SaceUserExceptionMessages.CONFIRMATION_CODE_IS_NOT_RELATED_TO_USER_WITH_ID.formatted(confirmationCode, requesterUserId)
            );
        }

        saceUserService.updateUserRolesById(Set.of(SaceUserRoleEnum.ROLE_USER), requesterUserId);
    }

    @Override
    @Transactional
    public SaceUserResponseDto register(AuthRegisterDataWithoutRolesDto credentials) {
        this.checkIfUserExists(credentials.email());

        String encodedPassword = passwordEncoder.encode(credentials.password());
        SaceUser newUser = makeUser(credentials, encodedPassword);
        SaceUser user = saceUserService.save(newUser);

        confirmationCodeService.generate(user.getId());
        return new SaceUserResponseDto(user.getEmail(), user.getName());
    }

    @Override
    @Transactional
    public SaceUserResponseDto registerWithRoles(AuthRegisterDataWithRolesDto credentials) {
        this.checkIfUserExists(credentials.email());

        String temporaryPassword = RandomStringGenerator.randomIncludingSpecialCharacters(12);

        String encodedPassword = passwordEncoder.encode(temporaryPassword);
        SaceUser newUser = makeUserWithRoles(credentials, encodedPassword);
        SaceUser user = saceUserService.save(newUser);

        mailService.sendSignUpTemporaryPasswordEmail(user.getName(), temporaryPassword, user.getEmail());
        return new SaceUserResponseDto(user.getEmail(), user.getName());
    }

    private void checkIfUserExists(String username) {
        if (saceUserService.existsByEmail(username)) {
            throw new SaceConflictException(
                    String.format(SaceUserExceptionMessages.USER_WITH_EMAIL_ALREADY_EXISTS, username)
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
                data.studentId(),
                encodedPassword,
                data.userRoles()
        );
    }

}
