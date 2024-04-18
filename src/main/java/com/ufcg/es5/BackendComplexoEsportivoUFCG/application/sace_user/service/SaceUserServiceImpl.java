package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.repository.SaceUserRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserDataDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserNameEmailDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceUserDataProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceUserNameEmailProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceForbiddenException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SaceUserServiceImpl implements SaceUserService {

    @Autowired
    private SaceUserRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpaRepository<SaceUser, Long> getRepository() {
        return repository;
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public boolean existsByStudentId(String studentId) {
        return repository.findByStudentId(studentId).isPresent();
    }

    @Override
    public Optional<SaceUser> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Long> findIdByEmail(String email) {
        return repository.findIdByEmail(email);
    }

    @Override
    public List<SaceUserDataDto> findAllAsDto() {
        Collection<SaceUserDataProjection> projections = repository.findAllUsersAsDto();
        return projections.stream().map(SaceUserDataDto::new).toList();
    }

    @Override
    public SaceUserNameEmailDto findNameEmailById(Long id) {
        SaceUserNameEmailProjection projection = repository.findNameEmailById(id);
        return new SaceUserNameEmailDto(projection);
    }

    @Override
    @Transactional
    public void updateUserRolesById(Set<SaceUserRoleEnum> roleUser, Long id) {
        SaceUser user = findById(id).orElseThrow(
                () -> new SaceResourceNotFoundException(String.format(SaceUserExceptionMessages.USER_WITH_ID_NOT_FOUND, id))
        );

        user.getRoleEnums().remove(SaceUserRoleEnum.ROLE_PENDING);
        user.getRoleEnums().add(SaceUserRoleEnum.ROLE_USER);
        repository.save(user);
    }

    @Override
    public Optional<SaceUser> findByStudentId(String studentId) {
        return repository.findByStudentId(studentId);
    }

    @Override
    public SaceUserNameEmailDto findByEmailAsDto(String email) {
        SaceUser user = findByEmail(email).orElseThrow();
        return objectMapper.convertValue(user, SaceUserNameEmailDto.class);
    }

    @Override
    @Transactional
    public void updatePasswordById(Long id, String currentPassword, String newPassword) {
        SaceUser user = findById(id).orElseThrow(
                () -> new SaceResourceNotFoundException(
                        SaceUserExceptionMessages.USER_WITH_ID_NOT_FOUND.formatted(id)
                )
        );

        checkIfPasswordMatches(user, currentPassword);

        user.setPassword(newPassword);

        save(user);
    }

    private void checkIfPasswordMatches(SaceUser user, String providedPassword) {
        if (!user.getPassword().equals(providedPassword)) {
            throw new SaceForbiddenException(
                    SaceUserExceptionMessages.WRONG_PASSWORD_FOR_USER_WITH_ID.formatted(user.getId())
            );
        }
    }

}