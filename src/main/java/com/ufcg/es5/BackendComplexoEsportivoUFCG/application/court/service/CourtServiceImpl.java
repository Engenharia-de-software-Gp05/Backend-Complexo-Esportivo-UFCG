package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository.CourtRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
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

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Override
    public JpaRepository<Court, Long> getRepository() {
        return this.repository;
    }

    @Override
    @Transactional
    public CourtResponseDto create(CourtSaveDto data) throws SaceConflictException {
        checkByName(data.name());
        System.out.println("oi");
        Court court = objectMapper.convertValue(data, Court.class);
        System.out.println(court.getCourtAvailabilityStatusEnum());
        System.out.println("oi2");
        this.save(court);
        System.out.println("oi3");
        return objectMapper.convertValue(court, CourtResponseDto.class);
    }

    @Override
    @Transactional
    public CourtResponseDto updateById(CourtUpdateDto data, Long id) throws SaceResourceNotFoundException, SaceConflictException {
        Court court = this.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(id)
        ));
        checkByName(data.name());

        court.setName(data.name());
        court.setCourtAvailabilityStatusEnum(data.courtStatusEnum());

        this.save(court);
        return objectMapper.convertValue(court, CourtResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws SaceResourceNotFoundException {
        checkIfExistsById(id);
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

    private void checkIfExistsById(Long id) {
        if (!this.exists(id)) {
            throw new SaceResourceNotFoundException(
                    CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(id)
            );
        }
    }

}
