package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;

import java.util.Optional;

public interface UserService extends BasicService<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByStudentId(String s);

    User findByEmail(String email);

    Optional<Long> findIdByEmail(String email);
}
