package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.repository.SaceUserRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserDataDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceUserDataProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SaceUserServiceImpl implements SaceUserService {

    @Autowired
    private SaceUserRepository repository;

    @Override
    public JpaRepository<SaceUser, Long> getRepository() {
        return repository;
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.findByEmail(email) != null;
    }

    @Override
    public boolean existsByStudentId(String studentId) {
        return repository.findByStudentId(studentId).isPresent();
    }

    @Override
    @Transactional
    public SaceUser findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional
    public Optional<Long> findIdByEmail(String email) {
        return repository.findIdByEmail(email);
    }

    @Override
    @Transactional
    public List<SaceUserDataDto> findAllUsersAsDto() {
        Collection<SaceUserDataProjection> projections = repository.findAllUsersAsDto();
        return projections.stream().map(SaceUserDataDto::new).toList();
    }

}
