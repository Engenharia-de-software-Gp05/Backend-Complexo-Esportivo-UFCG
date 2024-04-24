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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

public class GetTest extends BasicTestService {

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
    @DisplayName("display court success")
    void courtOfCatchbyNameSucess() {
        Assertions.assertEquals(1, courtService.findAll().size());

        Court response = courtService.findByName(court.getName());

        Assertions.assertEquals(court.getName(), response.getName());
    }

    @Test
    @Transactional
    @DisplayName("must return null in case of absence of block")
    void courtNameDoesNotExist() {
        Assertions.assertEquals(1, courtService.findAll().size());

        Court response = courtService.findByName("Quadra 999");

        Assertions.assertNull(response);
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
