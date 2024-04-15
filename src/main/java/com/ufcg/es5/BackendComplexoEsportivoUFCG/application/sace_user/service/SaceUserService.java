package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserDataDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserNameEmailDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SaceUserService extends BasicService<SaceUser, Long> {

    boolean existsByEmail(String email);

    boolean existsByStudentId(String studentId);

    Optional<SaceUser> findByEmail(String email);

    Optional<Long> findIdByEmail(String email);

    List<SaceUserDataDto> findAllAsDto();

    SaceUserNameEmailDto findNameEmailById(Long id);

    void updateUserRolesById(Set<SaceUserRoleEnum> roleUser, Long id);

    Optional<SaceUser> findByStudentId(String studentId);


    SaceUserNameEmailDto findByEmailAsDto(String email);

    Optional<SaceUser> findByEmailAndPassword(String email, String password);
}
