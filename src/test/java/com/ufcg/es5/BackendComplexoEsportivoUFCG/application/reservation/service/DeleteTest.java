package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.enums.ReservationAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserAccountStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
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

class DeleteTest extends BasicTestService {

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

    private static SaceUser user1;
    private static SaceUser user2;
    private static Court court;

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
    }

    @Test
    @Transactional
    @DisplayName("Deleting reservation by owner should be successful")
    void deleteReservationByOwnerShouldSucceed() {
        Reservation reservation = reservationService.save(createReservation());

        Assertions.assertEquals(1, reservationService.findAll().size());

        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user1.getId());
        reservationService.deleteById(reservation.getId());

        Assertions.assertEquals(0, reservationService.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("Deleting a non-existing reservation should throw ResourceNotFoundException")
    void deleteNonExistingReservationShouldThrowResourceNotFoundException() {
        Assertions.assertThrows(
                SaceResourceNotFoundException.class,
                () -> reservationService.deleteById(99999L)
        );
    }

    @Test
    @Transactional
    @DisplayName("Trying to delete reservation without ownership should throw IllegalAccessException")
    void deleteReservationWithoutOwnershipShouldThrowIllegalAccessException() {
        Reservation reservation = reservationService.save(createReservation());

        Assertions.assertEquals(1, reservationService.findAll().size());

        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user2.getId());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.deleteById(reservation.getId())
        );

        Assertions.assertEquals(1, reservationService.findAll().size());
    }

    private Reservation createReservation() {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plusHours(2L);
        return new Reservation(
                startDateTime,
                endDateTime,
                court,
                user1,
                ReservationAvailabilityStatusEnum.BOOKED
        );
    }

    private void createCourt() {
        court = new Court(
                COURT_NAME,
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE
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
                Set.of(SaceUserRoleEnum.ROLE_USER),
                SaceUserAccountStatusEnum.VALID
        );

        user1 = userService.save(user1);

        user2 = new SaceUser(
                USER_EMAIL_2,
                USER_USERNAME_2,
                USER_PHONE_NUMBER_2,
                USER_STUDENT_ID_2,
                USER_PASSWORD_2,
                Set.of(SaceUserRoleEnum.ROLE_USER),
                SaceUserAccountStatusEnum.VALID
        );

        user2 = userService.save(user2);
    }
}