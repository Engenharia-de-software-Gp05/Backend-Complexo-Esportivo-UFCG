package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterWithRolesRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.UserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.enums.UserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
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
    private UserService userService;

    public LoginResponseDto login(LoginRequestDto credentials) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDto(token);
    }

    @Transactional
    public UserResponseDto register(RegisterRequestDto credentials) {
        this.checkIfUserExists(credentials.email());

        String encodedPassword = new BCryptPasswordEncoder().encode(credentials.password());
        User newUser = makeUser(credentials, encodedPassword);
        User user = userService.save(newUser);
        return new UserResponseDto(user.getEmail(), user.getPassword());
    }

    @Transactional
    public UserResponseDto registerWithRoles(RegisterWithRolesRequestDto credentials) {
        this.checkIfUserExists(credentials.email());

        String encodedPassword = new BCryptPasswordEncoder().encode(credentials.password());
        User newUser = makeUserWithRoles(credentials, encodedPassword);
        User user = userService.save(newUser);

        return new UserResponseDto(user.getEmail(), user.getPassword());
    }

    private void checkIfUserExists(String username) {
        if (userService.existsByEmail(username)) {
            throw new IllegalArgumentException("Email already registered.");
        }
    }

    private User makeUser(RegisterRequestDto data, String encodedPassword) {
        return new User(
                data.email(),
                data.name(),
                data.phoneNumber(),
                data.studentId(),
                encodedPassword,
                Set.of(UserRoleEnum.ROLE_USER)
        );
    }

    private User makeUserWithRoles(RegisterWithRolesRequestDto data, String encodedPassword) {
        return new User(
                data.email(),
                data.name(),
                data.phoneNumber(),
                data.studentId(),
                encodedPassword,
                data.userRoles()
        );
    }
}
