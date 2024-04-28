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
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
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
import java.util.ArrayList;
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
    private static Court court1;
    private static Court court2;
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
    @DisplayName("Success in court update")
    void successfulUpdateOnTheCourt() {

        court1 = createCourtAvaliabe(COURT_NAME, COURT_IMAGE_URL);

        Reservation reservation = reservationService.save(createReservation(court1, user1, startDateTime));
        court1 = courtService.findByName(court1.getName());

        Assertions.assertEquals(1, reservationService.findAll().size());
        Assertions.assertEquals(court1.getName(), "Volleyball Court");
        Assertions.assertEquals(court1.getId(), reservationService.findAll().get(0).getCourt().getId());
        Assertions.assertEquals(courtService.findById(court1.getId()).get().getImagesUrls().size(), 1);

        CourtUpdateDto newCourt = new CourtUpdateDto(
                "Novo nome",
                CourtAvailabilityStatusEnum.UNAVAILABLE
        );

        courtService.updateById(newCourt, court1.getId());
        court1 = courtService.findByName(court1.getName());

        Assertions.assertEquals(1, reservationService.findAll().size());
        Assertions.assertEquals(court1.getName(), "Novo nome");
        Assertions.assertEquals(court1.getCourtAvailabilityStatusEnum(), newCourt.courtStatusEnum());
        Assertions.assertEquals(court1.getReservationDuration(), 90L);
    }

    @Test
    @Transactional
    @DisplayName("An exception should be returned because there is already a block with that name")
    void InvalidupdateCourtwithThatNameAlreadyExists() {
        startDateTime = startDateTime.minusHours(1);

        court1 = createCourtAvaliabe(COURT_NAME, COURT_IMAGE_URL);

        court2 = createCourtAvaliabe("Quadra Grande", COURT_IMAGE_URL);

        Reservation reservation1 = reservationService.save(createReservation(court2, user1, startDateTime));
        Reservation reservation2 = reservationService.save(createReservation(court1, user2, startDateTime));

        Assertions.assertEquals(2, reservationService.findAll().size());

        CourtUpdateDto newCourt = new CourtUpdateDto(
                "Quadra Grande",
                CourtAvailabilityStatusEnum.UNAVAILABLE
        );

        Assertions.assertThrows(
                SaceConflictException.class,
                () -> courtService.updateById(newCourt, court1.getId())
        );
        Assertions.assertEquals(reservationService.findByCourtId(court1.getId()).size(), 1);
        Assertions.assertEquals(2, reservationService.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("Returns exception for not finding blockl")
    void updateCourtInvalidParametersIdNotFoundThrowException() {
        court1 = createCourtAvaliabe(COURT_NAME, COURT_IMAGE_URL);

        CourtUpdateDto newCourt = new CourtUpdateDto(
                "Quadra Grande",
                CourtAvailabilityStatusEnum.UNAVAILABLE
        );

        Assertions.assertThrows(
                SaceResourceNotFoundException.class,
                () -> courtService.updateById(newCourt, 9999L)
        );
    }

    private Court createCourtAvaliabe(String name, String urlImage) {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(urlImage);

        Court court = new Court(
                name,
                imageUrls,
                CourtAvailabilityStatusEnum.AVAILABLE,
                90L,
                6L
        );

        return courtService.save(court);
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
