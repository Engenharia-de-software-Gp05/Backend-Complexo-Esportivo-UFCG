package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthUsernamePasswordDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithoutRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;

public interface AuthService {
    AuthTokenDto login(AuthUsernamePasswordDto data);

    AuthTokenDto register(AuthRegisterDataWithoutRolesDto data);

    SaceUserResponseDto registerByAdmin(AuthRegisterDataWithRolesDto data);

    void recoverPassword(String username);

    void updatePassword(String newPassword);

    void confirmEmailRegistered(String confirmationCode);
}
