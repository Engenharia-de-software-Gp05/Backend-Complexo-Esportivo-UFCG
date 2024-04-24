package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationServiceImpl;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceForbiddenException;
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

public class DeleteTest extends BasicTestService {

    private static final String COURT_NAME1 = "Volleyball Court";
    private static final String COURT_IMAGE_URL1 = "imageurl.com";
    private static final Long CANCELLATION_TIME_LIMIT = 24L;
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
    private static SaceUser user1;
    private static SaceUser user2;
    private static Court court1;
    private static Court court2;
    private static Court court3;
    private static Court court4;
    private static Court court5;
    private static Court court6;
    private static Court court7;
    private static LocalDateTime startDateTime;

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
        createUsers();
        startDateTime = LocalDateTime.now().plusHours(CANCELLATION_TIME_LIMIT).plusMinutes(1);
    }

    @Test
    @Transactional
    @DisplayName("Deleting the reservation by the owner must be successful and not delete the court")
    void TheOwnersDeletionOfTheReservationMustBeSuccessfulAndNotAffectTheCourt() {
        court1 = createCourtAvaliabe(COURT_NAME1, COURT_IMAGE_URL1);
        Reservation reservation = reservationService.save(createReservation(court1, user1, startDateTime));

        Assertions.assertEquals(1, reservationService.findAll().size());
        Assertions.assertEquals(1, courtService.findAll().size());

        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user1.getId());
        reservationService.delete(reservation.getId());

        Assertions.assertEquals(0, reservationService.findAll().size());
        Assertions.assertEquals(1, courtService.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("Delete a court when there is only one court in the system")
    void successfullyDeleteAQuatrainGAvaliabeivenThatThereIsOnlyOne() {
        court1 = createCourtAvaliabe(COURT_NAME1, COURT_IMAGE_URL1);

        Assertions.assertEquals(1, courtService.findAll().size());

        courtService.delete(court1);

        Assertions.assertEquals(0, courtService.findAll().size());
    }


    @Test
    @Transactional
    @DisplayName("Delete a court when there is only one court in the system and is unavailable")
    void successfullyDeleteAQuatrainUnavailableGivenThatThereIsOnlyOne() {
        court1 = createCourtUnavailable(COURT_NAME1, COURT_IMAGE_URL1);

        Assertions.assertEquals(1, courtService.findAll().size());

        courtService.delete(court1);

        Assertions.assertEquals(0, courtService.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("Delete a court by id when there is only one court in the system and is avaliabe")
    void successfullyDeleteAQuatrainGAvaliabeByIdGivenThatThereIsOnlyOne() {
        court1 = createCourtAvaliabe(COURT_NAME1, COURT_IMAGE_URL1);

        Assertions.assertEquals(1, courtService.findAll().size());

        courtService.deleteById(court1.getId());

        Assertions.assertEquals(0, courtService.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("Delete a court by id when there is only one court in the system and is unavailable")
    void successfullyDeleteAQuatrainUnavailableByIdGivenThatThereIsOnlyOne() {
        court1 = createCourtUnavailable(COURT_NAME1, COURT_IMAGE_URL1);

        Assertions.assertEquals(1, courtService.findAll().size());

        courtService.deleteById(court1.getId());

        Assertions.assertEquals(0, courtService.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("Delete a court when there is only one court in the system and is unavailable")
    void successfullyDeleteAQuatrainAvaliabeByIdGivenThatThereIsOnlyMany() {
        court1 = createCourtAvaliabe(COURT_NAME1, COURT_IMAGE_URL1);
        court2 = createCourtAvaliabe("court2", COURT_IMAGE_URL1);
        court3 = createCourtAvaliabe("court3", COURT_IMAGE_URL1);
        court4 = createCourtAvaliabe("court4", COURT_IMAGE_URL1);
        court5 = createCourtAvaliabe("court5", COURT_IMAGE_URL1);

        Assertions.assertEquals(0, reservationService.findAll().size());
        Assertions.assertEquals(5, courtService.findAll().size());

        Reservation reservation1 = reservationService.save(createReservation(court2, user1, startDateTime));
        Reservation reservation2 = reservationService.save(createReservation(court2, user2, startDateTime));
        Reservation reservation3 = reservationService.save(createReservation(court4, user1, startDateTime));

        Assertions.assertEquals(3, reservationService.findAll().size());
        Assertions.assertEquals(5, courtService.findAll().size());

        List<Court> courts= courtService.findAll();
        List<Reservation> reservations = reservationService.findAll();

        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user1.getId());
        reservationService.delete(reservation1.getId());

        Assertions.assertEquals(2, reservationService.findAll().size());
        Assertions.assertEquals(5, courtService.findAll().size());

        reservations.remove(reservation1);

        courtService.deleteById(court1.getId());

        courts.remove(court1);

        Assertions.assertEquals(2, reservationService.findAll().size());
        Assertions.assertEquals(4, courtService.findAll().size());

        courtService.deleteById(court4.getId());

        courts.remove(court4);
        reservations.remove(reservation3);

        Assertions.assertTrue(userService.findById(user1.getId()).isPresent());
        Assertions.assertEquals(1, reservationService.findAll().size());
        Assertions.assertEquals(3, courtService.findAll().size());
        Assertions.assertEquals(reservations, reservationService.findAll());
        Assertions.assertEquals(courts, courtService.findAll());
    }

    @Test
    @Transactional
    @DisplayName("Deletion without a referent block must return an exception")
    void deleteABlockThatDoesNotExistThrowException() {
        Assertions.assertThrows(
                SaceResourceNotFoundException.class,
                () -> courtService.deleteById(1000L)
        );
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

    private Reservation createReservation(Court court, SaceUser user, LocalDateTime startDateTime) {
        LocalDateTime endDateTime = startDateTime.plusHours(2L);
        return new Reservation(
                startDateTime,
                endDateTime,
                court,
                user
        );
    }

    private Court createCourtAvaliabe(String name, String urlImage) {
        court1 = new Court(
                name,
                List.of(urlImage),
                CourtAvailabilityStatusEnum.AVAILABLE,
                90L,
                6L
        );

        return courtService.save(court1);
    }

    private Court createCourtUnavailable(String name, String urlImage) {
        court1 = new Court(
                name,
                List.of(urlImage),
                CourtAvailabilityStatusEnum.UNAVAILABLE,
                90L,
                6L
        );

        return courtService.save(court1);
    }

}
