package com.ufcg.es5.BackendComplexoEsportivoUFCG.project.aluno.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Aluno;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.project.aluno.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AlunoServiceImpl implements AlunoService{
    @Autowired
    AlunoRepository repository;

    @Override
    public JpaRepository<Aluno, Long> getRepository() {
        return repository;
    }
}
