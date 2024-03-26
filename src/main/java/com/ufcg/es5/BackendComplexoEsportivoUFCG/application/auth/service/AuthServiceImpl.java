package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthUsernamePasswordDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithoutRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserAccountStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Transactional
    public AuthTokenDto login(AuthUsernamePasswordDto credentials) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService
                .generateToken((SaceUser) auth.getPrincipal(), EXPIRATION_TIME_FOR_LOGIN_TOKEN);

        return new AuthTokenDto(token);
    }

    @Override
    @Transactional
    public void recoverPassword(String username) {
        SaceUser user = saceUserService.findByEmail(username);

        var token = tokenService
                .generateToken(user, EXPIRATION_TIME_FOR_RECOVER_PASSWORD_TOKEN);

        System.out.println(token);
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
    public SaceUserResponseDto register(AuthRegisterDataWithoutRolesDto credentials) {
        this.checkIfUserExists(credentials.email());

        String encodedPassword = passwordEncoder.encode(credentials.password());
        SaceUser newUser = makeUser(credentials, encodedPassword);
        SaceUser user = saceUserService.save(newUser);
        return new SaceUserResponseDto(user.getEmail(), user.getPassword());
    }

    @Override
    @Transactional
    public SaceUserResponseDto registerWithRoles(AuthRegisterDataWithRolesDto credentials) {
        this.checkIfUserExists(credentials.email());

        String encodedPassword = passwordEncoder.encode(credentials.password());
        SaceUser newUser = makeUserWithRoles(credentials, encodedPassword);
        SaceUser user = saceUserService.save(newUser);
        return new SaceUserResponseDto(user.getEmail(), user.getPassword());
    }

    private void checkIfUserExists(String username) {
        if (saceUserService.existsByEmail(username)) {
            throw new SaceConflictException(SaceUserExceptionMessages.USER_WITH_EMAIL_ALREADY_EXISTS);
        }
    }

    private SaceUser makeUser(AuthRegisterDataWithoutRolesDto data, String encodedPassword) {
        return new SaceUser(
                data.email(),
                data.name(),
                data.phoneNumber(),
                data.studentId(),
                encodedPassword,
                Set.of(SaceUserRoleEnum.ROLE_USER),
                SaceUserAccountStatusEnum.PENDING
        );
    }

    private SaceUser makeUserWithRoles(AuthRegisterDataWithRolesDto data, String encodedPassword) {
        return new SaceUser(
                data.email(),
                data.name(),
                data.phoneNumber(),
                data.studentId(),
                encodedPassword,
                data.userRoles(),
                SaceUserAccountStatusEnum.PENDING
        );
    }
}
