package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationServiceImpl;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtUpdateDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceForbiddenException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.court.CourtExceptionMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class UpdateTest extends BasicTestService {

    private static final String USER_EMAIL_1 = "user@gmail.com";
    private static final String USER_USERNAME_1 = "username";
    private static final String USER_PHONE_NUMBER_1 = "44662954915";
    private static final String USER_STUDENT_ID_1 = "1212135265";
    private static final String USER_PASSWORD_1 = "5451616545";
    private static final String USER_EMAIL_2 = "user2@gmail.com";
    private static final String USER_USERNAME_2 = "username2";
    private static final String USER_PHONE_NUMBER_2 = "44662954917";
    private static final String USER_STUDENT_ID_2 = "1212135267";
    private static final String USER_PASSWORD_2 = "5451616547";
    private static final String COURT_NAME = "Volleyball Court";
    private static final String COURT_IMAGE_URL = "imageurl.com";
    private static final Long CANCELLATION_TIME_LIMIT = 24L;
    private static SaceUser user1;
    private static SaceUser user2;
    private static Court court;
    private static LocalDateTime startDateTime;

//    @Override
//    @jakarta.transaction.Transactional
//    public CourtResponseDto updateById(CourtUpdateDto data, Long id) throws SaceResourceNotFoundException {
//        Court court = this.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
//                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(id)
//        ));
//        updateCourtData(court, data);
//        this.save(court);
//        return objectMapper.convertValue(court, CourtResponseDto.class);
//    }


    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private SaceUserService userService;

    @MockBean
    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        createCourt();
        createUsers();
        startDateTime = LocalDateTime.now().plusHours(CANCELLATION_TIME_LIMIT).plusMinutes(1);
    }

    @Test
    @Transactional
    @DisplayName("Deleting reservation by owner should be successful")
    void successfulUpdateOnTheCourt() {
        Reservation reservation = reservationService.save(createReservation(court, user1, startDateTime));

        court = courtService.findByName(court.getName());

        Assertions.assertEquals(1, reservationService.findAll().size());
        Assertions.assertEquals(court.getName(), "Volleyball Court");
        Assertions.assertEquals(court.getImagesUrls().get(0), "imageurl.com");
        Assertions.assertEquals(court.getReservations().size(), 1);
        Assertions.assertTrue(court.getReservations().contains(reservation));

        CourtUpdateDto newCourt = new CourtUpdateDto(
                "Novo nome",
                List.of("LINK"),
                CourtAvailabilityStatusEnum.UNAVAILABLE
        );

        courtService.updateById(newCourt, court.getId());

        Assertions.assertEquals(1, reservationService.findAll().size());
        Assertions.assertEquals(court.getName(), "Novo nome");
        Assertions.assertEquals(court.getImagesUrls().get(0), "LINK.com");
        Assertions.assertEquals(court.getReservations().size(), 1);
        Assertions.assertTrue(court.getReservations().contains(reservation));
    }

    @Test
    @Transactional
    @DisplayName("Returns exception for not finding blockl")
    void updateCourtInvalidParametersThrowException() {
        startDateTime = startDateTime.minusHours(1);

        Reservation reservation = reservationService.save(createReservation(court, user1, startDateTime));

        Assertions.assertEquals(1, reservationService.findAll().size());

        CourtUpdateDto newCourt = new CourtUpdateDto(
                "Quadra Grande",
                List.of("imagem.com"),
                CourtAvailabilityStatusEnum.UNAVAILABLE
        );

        Assertions.assertThrows(
                SaceResourceNotFoundException.class,
                () -> courtService.updateById(newCourt, 999L)
        );
    }

//    @Test
//    @Transactional
//    @DisplayName("Deleting reservation by owner should be successful")
//    void updateCourtWithReservationsInvalidParametersThrowException() {
//        startDateTime = startDateTime.minusHours(1);
//
//        Reservation reservation = reservationService.save(createReservation(startDateTime));
//
//        Assertions.assertEquals(1, reservationService.findAll().size());
//
//        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user1.getId());
//
//        Assertions.assertThrows(
//                SaceForbiddenException.class,
//                () -> reservationService.delete(reservation.getId())
//        );
//    }
//
//
//    @Test
//    @Transactional
//    @DisplayName("Deleting reservation by owner should be successful")
//    void updateCourtNotFoundThrowException() {
//        startDateTime = startDateTime.minusHours(1);
//
//        Reservation reservation = reservationService.save(createReservation(startDateTime));
//
//        Assertions.assertEquals(1, reservationService.findAll().size());
//
//        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user1.getId());
//
//        Assertions.assertThrows(
//                SaceForbiddenException.class,
//                () -> reservationService.delete(reservation.getId())
//        );
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deleting reservation by owner should be successful")
//    void deleteWhenCancellationTimeExpiredShouldThrowException() {
//        startDateTime = startDateTime.minusHours(1);
//
//        Reservation reservation = reservationService.save(createReservation(startDateTime));
//
//        Assertions.assertEquals(1, reservationService.findAll().size());
//
//        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user1.getId());
//
//        Assertions.assertThrows(
//                SaceForbiddenException.class,
//                () -> reservationService.delete(reservation.getId())
//        );
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Deleting a non-existing reservation should throw ResourceNotFoundException")
//    void deleteNonExistingReservationShouldThrowResourceNotFoundException() {
//        Assertions.assertThrows(
//                SaceResourceNotFoundException.class,
//                () -> reservationService.delete(99999L)
//        );
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Trying to delete reservation without ownership should throw IllegalAccessException")
//    void deleteReservationWithoutOwnershipShouldThrowIllegalAccessException() {
//        LocalDateTime startDateTime = LocalDateTime.now().plusHours(25L);
//
//        Reservation reservation = reservationService.save(createReservation(startDateTime));
//
//        Assertions.assertEquals(1, reservationService.findAll().size());
//
//        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user2.getId());
//
//        Assertions.assertThrows(
//                SaceForbiddenException.class,
//                () -> reservationService.delete(reservation.getId())
//        );
//
//        Assertions.assertEquals(1, reservationService.findAll().size());
//    }

    private Reservation createReservation(Court court, SaceUser user, LocalDateTime startDateTime) {
        LocalDateTime endDateTime = startDateTime.plusHours(2L);
        return new Reservation(
                startDateTime,
                endDateTime,
                court,
                user
        );
    }

    private void createCourt() {
        court = new Court(
                COURT_NAME,
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE,
                90L,
                6L
        );

        court = courtService.save(court);
    }

    private void createUsers() {
        user1 = new SaceUser(
                USER_EMAIL_1,
                USER_USERNAME_1,
                USER_PHONE_NUMBER_1,
                USER_STUDENT_ID_1,
                USER_PASSWORD_1,
                Set.of(SaceUserRoleEnum.ROLE_USER)
        );

        user1 = userService.save(user1);

        user2 = new SaceUser(
                USER_EMAIL_2,
                USER_USERNAME_2,
                USER_PHONE_NUMBER_2,
                USER_STUDENT_ID_2,
                USER_PASSWORD_2,
                Set.of(SaceUserRoleEnum.ROLE_USER)
        );

        user2 = userService.save(user2);
    }
}
