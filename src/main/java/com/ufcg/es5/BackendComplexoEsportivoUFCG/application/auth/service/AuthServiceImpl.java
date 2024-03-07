package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.UserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    public LoginResponseDto login(LoginRequestDto credentials) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDto(token);
    }

    @Transactional
    public UserResponseDto register(RegisterRequestDto credentials){
        this.checkIfUserExists(credentials);

        String encodedPassword = new BCryptPasswordEncoder().encode(credentials.password());
        User newUser = makeUser(credentials, encodedPassword);
        User user = userService.save(newUser);
        return new UserResponseDto(user.getEmail(), user.getPassword());
    }

    private void checkIfUserExists(RegisterRequestDto data){
        if(userService.existsByEmail(data.email())){
            throw new IllegalArgumentException("Email already registered.");
        }
        if(userService.existsByStudentId(data.studentId())){
            throw new IllegalArgumentException("Student id already registered.");
        }
    }
    private User makeUser(RegisterRequestDto data, String encodedPassword){
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
