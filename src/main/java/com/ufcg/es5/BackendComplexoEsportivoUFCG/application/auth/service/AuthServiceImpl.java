package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.UserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
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

    public LoginResponseDto login(LoginRequestDto data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        System.out.println("caralho userPass: " + usernamePassword);
        var auth = this.authenticationManager.authenticate(usernamePassword);
        System.out.println("caralho: auth" + auth);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        System.out.println("caralho: token" + token);

        return new LoginResponseDto(token);
    }

    public UserResponseDto register(RegisterRequestDto data){
        validateRegisterData(data);

        String encodedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = makeUser(data, encodedPassword);
        User user = userService.save(newUser);
        return new UserResponseDto(user.getEmail(), user.getPassword());
    }

    private void validateRegisterData(RegisterRequestDto data){
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
