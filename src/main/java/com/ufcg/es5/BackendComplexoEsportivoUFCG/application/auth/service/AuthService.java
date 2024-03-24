package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterWithRolesRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto data);

    SaceUserResponseDto register(RegisterRequestDto data);

    SaceUserResponseDto registerWithRoles(RegisterWithRolesRequestDto data);
}
