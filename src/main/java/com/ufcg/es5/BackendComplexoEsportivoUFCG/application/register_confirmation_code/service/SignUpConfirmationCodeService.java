package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SignUpConfirmationCode;

public interface SignUpConfirmationCodeService extends BasicService<SignUpConfirmationCode, Long> {

    void generate(Long userId);

    SignUpConfirmationCode findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    SignUpConfirmationCode findByUserIdAndConfirmationCode(Long userId, String confirmationCode);

    boolean existsByUserIdAndConfirmationCode(Long userId, String confirmationCode);

    void save(Long userId, String confirmationCode);

    void collect();
}
