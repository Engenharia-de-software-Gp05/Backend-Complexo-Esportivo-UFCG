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
import java.util.Optional;

@Service
public class SignUpConfirmationCodeServiceImpl implements SignUpConfirmationCodeService {


    private static final long EXPIRATION_TIME = 5L;
    private static final int CONFIRMATION_CODE_SIZE = 6;

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
    public void generate(Long userId) {
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(EXPIRATION_TIME);

        SignUpConfirmationCode confirmationCode = save(userId, expiresAt);

        publishSavedEvent(userId, confirmationCode.getConfirmationCode());
    }

    private void publishSavedEvent(Long userId, String confirmationCode) {
        SignUpConfirmationCodeUserIdConfirmationCodeDto userIdConfirmationCodeDto = new SignUpConfirmationCodeUserIdConfirmationCodeDto(userId, confirmationCode);
        SavedEvent<SignUpConfirmationCodeUserIdConfirmationCodeDto> savedEvent = new SavedEvent<>(
                userIdConfirmationCodeDto, SignUpConfirmationCodeUserIdConfirmationCodeDto.class
        );

        eventPublisher.publishEvent(savedEvent);
    }


    private void checkIfUserHasActiveCode(SignUpConfirmationCode confirmationCode, Long userId) {
        LocalDateTime currentTime = LocalDateTime.now();
        checkIfUserHasActiveCode(confirmationCode, currentTime, userId);
    }

    private void checkIfUserHasActiveCode(SignUpConfirmationCode confirmationCode, LocalDateTime currentTime, Long userId) {
        if (confirmationCode.getExpiresAt().isBefore(currentTime)) {
            throw new SaceConflictException(
                    String.format(SaceUserExceptionMessages.USER_WITH_ID_ALREADY_HAS_AN_ACTIVE_CODE, userId)
            );
        }
    }

    @Override
    public Optional<SignUpConfirmationCode> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return this.findByUserId(userId).isEmpty();
    }

    @Override
    public Optional<SignUpConfirmationCode> findByUserIdAndConfirmationCode(Long userId, String confirmationCode) {
        return repository.findByUserIdAndConfirmationCode(userId, confirmationCode);
    }

    @Override
    public boolean existsByUserIdAndConfirmationCode(Long userId, String confirmationCode) {
        return this.findByUserIdAndConfirmationCode(userId, confirmationCode).isPresent();
    }

    @Override
    @Transactional
    public SignUpConfirmationCode save(Long userId, LocalDateTime expiresAt) {

        SaceUser user = userService.findById(userId).orElseThrow(
                () -> new SaceResourceNotFoundException(String.format(SaceUserExceptionMessages.USER_WITH_ID_NOT_FOUND, userId))
        );

        SignUpConfirmationCode signUpConfirmationCode = findByUserId(userId).orElseGet(
                () -> new SignUpConfirmationCode(expiresAt)
        );

        checkIfUserHasActiveCode(signUpConfirmationCode, userId);
        String confirmationCode = RandomStringGenerator.randomAlphaNumeric(CONFIRMATION_CODE_SIZE);

        signUpConfirmationCode.setUser(user);
        signUpConfirmationCode.setConfirmationCode(confirmationCode);

        return this.repository.save(signUpConfirmationCode);
    }

    @Override
    @Transactional
    public void collect(LocalDateTime dateTime) {
        Collection<Long> confirmationCodesToDelete = repository.findAllBeforeDateTime(dateTime.minusMinutes(5L));
        repository.deleteAllById(confirmationCodesToDelete);
    }
}
