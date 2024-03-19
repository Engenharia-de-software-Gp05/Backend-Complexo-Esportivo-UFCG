package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Override
    public JpaRepository<User, Long> getRepository() {
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
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional
    public Optional<Long> findIdByEmail(String email) {
        return repository.findIdByEmail(email);
    }

}
