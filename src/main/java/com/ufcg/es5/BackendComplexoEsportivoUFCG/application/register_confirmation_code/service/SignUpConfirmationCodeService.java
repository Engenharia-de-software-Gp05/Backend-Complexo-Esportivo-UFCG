package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sign_up_confirmation_code.SignUpConfirmationCodeSavedEventDataDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SignUpConfirmationCode;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SignUpConfirmationCodeService extends BasicService<SignUpConfirmationCode, Long> {

    void generate(Long userId);

    Optional<SignUpConfirmationCode> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    Optional<SignUpConfirmationCode> findByUserIdAndConfirmationCode(Long userId, String confirmationCode);

    boolean existsByUserIdAndConfirmationCode(Long userId, String confirmationCode);

    SignUpConfirmationCodeSavedEventDataDto save(Long userId, LocalDateTime expiresAt);

    void collect(LocalDateTime dateTime);

    void resendConfirmationCodeByUserId(Long authenticatedUserId);
}
