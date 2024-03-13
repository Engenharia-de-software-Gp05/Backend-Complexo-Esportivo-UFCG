package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CourtServiceImpl implements CourtService{

    @Autowired
    private CourtRepository repository;

    @Override
    public JpaRepository<Court, Long> getRepository() {
        return repository;
    }

}
