package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository.CourtRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtUpdateDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.court.CourtExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.handler.SystemInternalException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.court.CourtExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.handler.SystemInternalException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CourtServiceImpl implements CourtService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourtRepository repository;

    @Override
    public JpaRepository<Court, Long> getRepository() {
        return this.repository;
    }

    @Override
    @Transactional
    public CourtResponseDto create(CourtSaveDto data) throws SaceConflictException {
        checkByName(data.name());
        Court court = objectMapper.convertValue(data, Court.class);
        repository.save(court);
        return objectMapper.convertValue(court, CourtResponseDto.class);
    }

    @Override
    @Transactional
    public CourtResponseDto updateById(CourtUpdateDto data, Long id) throws SaceResourceNotFoundException {
        Court court = this.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(id)
        ));
        updateCourtData(court, data);
        repository.save(court);
        return objectMapper.convertValue(court, CourtResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws SaceResourceNotFoundException {
        repository.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(id)
        ));
        repository.deleteById(id);
    }
  
    @Override
    public Court findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Boolean existsByName(String name) {
        return findByName(name) != null;
    }

    private void checkByName(String name) {
        if (existsByName(name)) {
            throw new SaceConflictException(
                    CourtExceptionMessages.COURT_WITH_NAME_ALREADY_EXISTS.formatted(name)
            );
        }
    }
    private void updateCourtData(Court court, CourtUpdateDto newData) {
        court.setName(newData.name());
        court.setCourtAvailabilityStatusEnum(newData.courtStatusEnum());
        court.getImagesUrls().clear();
        court.getImagesUrls().addAll(newData.imagesUrls());
    }

}
