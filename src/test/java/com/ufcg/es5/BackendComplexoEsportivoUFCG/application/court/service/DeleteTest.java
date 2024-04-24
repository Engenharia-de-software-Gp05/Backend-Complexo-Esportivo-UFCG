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
//    void deleteById(Long id);
//
//    @Override
//    @Transactional
//    public void deleteById(Long id) throws SaceResourceNotFoundException {
//        checkIfExistsById(id);
//        repository.deleteById(id);
//    }
//
//    private void checkIfExistsById(Long id) {
//        if (this.exists(id)) {
//            throw new SaceResourceNotFoundException(
//                    CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(id)
//            );
//        }
//    }

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

    //Deletar reserva não deleta quadra - OK

    //Deletar sucesso apenas um

    //Deletar suesso com reservas deve exluir as reservas

    //Deletar sucesso com mais de um com e sem reservas

    //Deletar fracasso Id não existe

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
        Reservation reservation2 = reservationService.save(createReservation(court2, user1, startDateTime));
        Reservation reservation3 = reservationService.save(createReservation(court4, user2, startDateTime));

        List<Reservation> reservations = reservationService.findAll()
                
        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user1.getId());
        reservationService.delete(reservation1.getId());

        List<Court> courts = courtService.findAll();

        courtService.deleteById(court1.getId());

        courtService.deleteById(court4.getId());

        reservations.remove(reservation1);
        reservations.remove(reservation3);

        courts.remove(court1);
        courts.remove(court4);

        Assertions.assertEquals(courts, courtService.findAll());
        Assertions.assertEquals(reservations, reservationService.findAll());
    }

    @Test
    @Transactional
    @DisplayName("Delete a court by id when there is only one court in the system and is avaliabe")
    void successfullyDeleteAQuatrainGAvaliabeByIdGivenThatThereIsOnlyMany() {
        court1 = createCourtAvaliabe(COURT_NAME1, COURT_IMAGE_URL1);

        Assertions.assertEquals(1, courtService.findAll().size());

        courtService.deleteById(court1.getId());

        Assertions.assertEquals(0, courtService.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("Deleting reservation by owner should be successful")
    void deleteWhenCancellationTimeExpiredShouldThrowException() {
        startDateTime = startDateTime.minusHours(1);

        Reservation reservation = reservationService.save(createReservation(court1, user1, startDateTime));

        Assertions.assertEquals(1, reservationService.findAll().size());

        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user1.getId());

        Assertions.assertThrows(
                SaceForbiddenException.class,
                () -> reservationService.delete(reservation.getId())
        );
    }

    @Test
    @Transactional
    @DisplayName("Deleting a non-existing reservation should throw ResourceNotFoundException")
    void deleteNonExistingReservationShouldThrowResourceNotFoundException() {
        Assertions.assertThrows(
                SaceResourceNotFoundException.class,
                () -> reservationService.delete(99999L)
        );
    }

    @Test
    @Transactional
    @DisplayName("Trying to delete reservation without ownership should throw IllegalAccessException")
    void deleteReservationWithoutOwnershipShouldThrowIllegalAccessException() {
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(25L);

        Reservation reservation = reservationService.save(createReservation(court1, user1, startDateTime));

        Assertions.assertEquals(1, reservationService.findAll().size());

        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user2.getId());

        Assertions.assertThrows(
                SaceForbiddenException.class,
                () -> reservationService.delete(reservation.getId())
        );

        Assertions.assertEquals(1, reservationService.findAll().size());
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
