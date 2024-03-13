package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtPostRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository.CourtRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.ComplexoEspException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CourtServiceImpl implements CourtService{

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private CourtRepository repository;

    @Override
    public JpaRepository<Court, Long> getRepository() {
        return this.repository;
    }

    @Override
    public CourtResponseDto create(CourtSaveDto data) {
        checkByName(data.name());
        Court court = objectMapper.convertValue(data, Court.class);
        repository.save(court);
        CourtResponseDto response = objectMapper.convertValue(court, CourtResponseDto.class);
        return response;
    }

    @Override
    public Court findByName(String name) {return repository.findByName(name);}

    @Override
    public Boolean existsByName(String name) {return findByName(name) != null;}

    private void checkByName(String name) {
        if(existsByName(name)) {throw new ComplexoEspException();}
    }

}
