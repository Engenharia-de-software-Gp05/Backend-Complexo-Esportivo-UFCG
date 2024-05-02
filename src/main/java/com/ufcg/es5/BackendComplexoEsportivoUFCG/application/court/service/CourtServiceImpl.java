package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository.CourtRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.*;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtBasicProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtDetailedProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtIdNameProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.court.CourtExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.s3.S3Uploader;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Service
public class CourtServiceImpl implements CourtService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourtRepository repository;

    @Autowired
    private S3Uploader s3Uploader;

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
        Court court = saveDtoToClass(data);
        this.save(court);
        return classToResponseDto(court);
    }

    private CourtResponseDto classToResponseDto(Court court) {
        return new CourtResponseDto(court.getName(), court.getImagesUrls(), court.getCourtAvailabilityStatusEnum());
    }

    private Court saveDtoToClass(CourtSaveDto data) {
        return new Court(
                data.name(),
                data.reservationDuration(),
                data.minimumIntervalBetweenReservation());
    }

    @Override
    @Transactional
    public CourtResponseDto updateById(CourtUpdateDto data, Long id) throws SaceResourceNotFoundException, SaceConflictException {
        Court court = getCourtById(id);
        checkByName(data.name());

        court.setName(data.name());
        court.setCourtAvailabilityStatusEnum(data.courtStatusEnum());

        this.save(court);
        return classToResponseDto(court);
    }

    private Court getCourtById(Long id) {
        return this.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(id)
        ));
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
    public CourtDetailedResponseDto findCourtDetailedResponseDtoById(Long id) {
        CourtDetailedProjection projection = repository.findCourtDetailedProjectionById(id);
        return new CourtDetailedResponseDto(projection);
    }

    @Override
    public Collection<CourtBasicResponseDto> findAllCourtBasicResponseDto() {
        Collection<CourtBasicProjection> projections = repository.findAllCourtBasicProjection();
        return projections.stream().map(CourtBasicResponseDto::new).toList();
    }

    @Override
    public Boolean existsByName(String name) {
        return findByName(name) != null;
    }

    @Override
    @Transactional
    public void updateImageById(MultipartFile picture, Long id) {
        Court court = getCourtById(id);

        String courtImageUrl = s3Uploader.uploadCourtImage(picture);
        court.addImageUrl(courtImageUrl);
        save(court);
    }

    @Override
    public Collection<CourtIdNameDto> findAllAsDto() {
        Collection<CourtIdNameProjection> projections = repository.findIdName();
        return projections.stream().map(CourtIdNameDto::new).toList();
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
