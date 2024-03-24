package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.repository.SaceUserRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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

}
