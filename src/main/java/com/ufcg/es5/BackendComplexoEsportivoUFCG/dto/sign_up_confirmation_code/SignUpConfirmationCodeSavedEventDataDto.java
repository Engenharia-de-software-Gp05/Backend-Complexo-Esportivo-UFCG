package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sign_up_confirmation_code;

public record SignUpConfirmationCodeSavedEventDataDto(
        String name,
        String email,
        String confirmationCode
) {
}
