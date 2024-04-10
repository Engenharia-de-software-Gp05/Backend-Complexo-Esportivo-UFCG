package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.*;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;

public interface AuthService {
    AuthTokenDto login(AuthUsernamePasswordDto data);

    AuthTokenDto register(AuthRegisterDataWithoutRolesDto data);

    SaceUserResponseDto registerByAdmin(AuthRegisterDataWithRolesDto data);

    void recoverPassword(String username);

    void updatePassword(AuthPasswordUpdateDto passwordUpdateDto);

    void confirmEmailRegistered(String confirmationCode);
}
