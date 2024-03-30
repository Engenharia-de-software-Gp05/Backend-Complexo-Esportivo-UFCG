package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event.SavedEvent;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.repository.SignUpConfirmationCodeRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sign_up_confirmation_code.SignUpConfirmationCodeUserIdConfirmationCodeDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SignUpConfirmationCode;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.RandomStringGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class SignUpConfirmationCodeServiceImpl implements SignUpConfirmationCodeService{


    @Autowired
    private SignUpConfirmationCodeRepository repository;

    @Autowired
    private SaceUserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public JpaRepository<SignUpConfirmationCode, Long> getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public void generateAndSend(Long userId) {
        checkIfUserAlreadyHasCode(userId);

        String confirmationCode = RandomStringGenerator.randomAlphaNumeric(6);
        save(userId, confirmationCode);

        SignUpConfirmationCodeUserIdConfirmationCodeDto userIdConfirmationCodeDto = new SignUpConfirmationCodeUserIdConfirmationCodeDto(userId, confirmationCode);
        SavedEvent<SignUpConfirmationCodeUserIdConfirmationCodeDto> savedEvent = new SavedEvent<>(
                userIdConfirmationCodeDto, SignUpConfirmationCodeUserIdConfirmationCodeDto.class
        );

        eventPublisher.publishEvent(savedEvent);
    }

    private void checkIfUserAlreadyHasCode(Long userId) {
        if(existsByUserId(userId)){
            throw new SaceConflictException(
                    SaceUserExceptionMessages.USER_WITH_ID_ALREADY_HAS_AN_ACTIVE_CODE.formatted(userId)
            );
        }
    }

    @Override
    public SignUpConfirmationCode findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return this.findByUserId(userId) != null;
    }

    @Override
    public SignUpConfirmationCode findByUserIdAndConfirmationCode(Long userId, String confirmationCode) {
        return repository.findByUserIdAndConfirmationCode(userId, confirmationCode);
    }

    @Override
    public boolean existsByUserIdAndConfirmationCode(Long userId, String confirmationCode) {
        return this.findByUserIdAndConfirmationCode(userId, confirmationCode) != null;
    }

    @Override
    @Transactional
    public void save(Long userId, String confirmationCode) {
        SaceUser user = userService.findById(userId).orElseThrow(
                () -> new SaceResourceNotFoundException(SaceUserExceptionMessages.USER_WITH_ID_NOT_FOUND.formatted(userId))
        );

        SignUpConfirmationCode signUpConfirmationCode = new SignUpConfirmationCode(user, confirmationCode);
        repository.save(signUpConfirmationCode);
    }

    @Override
    @Transactional
    public void collect() {
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(5);
        collect(localDateTime);
    }

    private void collect(LocalDateTime dateTime){
        Collection<Long> confirmationCodesToDelete = repository.findAllBeforeDateTime(dateTime);
        repository.deleteAllById(confirmationCodesToDelete);
    }
}
