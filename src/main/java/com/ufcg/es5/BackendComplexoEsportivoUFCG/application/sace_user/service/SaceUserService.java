package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserDataResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;

import java.util.List;
import java.util.Optional;

public interface SaceUserService extends BasicService<SaceUser, Long> {

    boolean existsByEmail(String email);

    boolean existsByStudentId(String s);

    SaceUser findByEmail(String email);

    Optional<Long> findIdByEmail(String email);

    List<SaceUserDataResponseDto> findAllUsersAsDto();
}
