package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterWithRolesRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserAccountStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SaceUserService saceUserService;

    @Transactional
    public LoginResponseDto login(LoginRequestDto credentials) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((SaceUser) auth.getPrincipal());

        return new LoginResponseDto(token);
    }

    @Transactional
    public SaceUserResponseDto register(RegisterRequestDto credentials) {
        this.checkIfUserExists(credentials.email());

        String encodedPassword = new BCryptPasswordEncoder().encode(credentials.password());
        SaceUser newUser = makeUser(credentials, encodedPassword);
        SaceUser user = saceUserService.save(newUser);

        return new SaceUserResponseDto(user.getEmail(), user.getPassword());
    }

    @Transactional
    public SaceUserResponseDto registerWithRoles(RegisterWithRolesRequestDto credentials) {
        this.checkIfUserExists(credentials.email());

        String encodedPassword = new BCryptPasswordEncoder().encode(credentials.password());
        SaceUser newUser = makeUserWithRoles(credentials, encodedPassword);
        SaceUser user = saceUserService.save(newUser);
        return new SaceUserResponseDto(user.getEmail(), user.getPassword());
    }

    private void checkIfUserExists(String username) {
        if (saceUserService.existsByEmail(username)) {
            throw new IllegalArgumentException("Email already registered.");
        }
    }

    private SaceUser makeUser(RegisterRequestDto data, String encodedPassword) {
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

    private SaceUser makeUserWithRoles(RegisterWithRolesRequestDto data, String encodedPassword) {
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