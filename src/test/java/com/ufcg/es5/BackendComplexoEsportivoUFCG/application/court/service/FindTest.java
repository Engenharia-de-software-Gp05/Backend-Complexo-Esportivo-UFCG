package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationServiceImpl;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtBasicResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtDetailedResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


class FindTest extends BasicTestService {

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
    @DisplayName("display court success")
    void courtOfCatchbyNameSucess() {
        List<String> imagens = new ArrayList<String>();

        imagens.add("img1.com");
        imagens.add("img2.com");

        Court newCourt1 = new Court(
                "Novo nome",
                imagens,
                CourtAvailabilityStatusEnum.UNAVAILABLE,
                90L,
                10L
        );

        court1 = courtService.save(newCourt1);

        Assertions.assertEquals(1, courtService.findAll().size());

        Court response = courtService.findByName(court1.getName());

        Assertions.assertEquals(court1.getName(), response.getName());
    }

    @Test
    @Transactional
    @DisplayName("must return null in case of absence of block")
    void courtNameDoesNotExist() {

        Court newCourt1 = new Court(
                "Novo nome",
                new ArrayList<>(),
                CourtAvailabilityStatusEnum.UNAVAILABLE,
                90L,
                10L
        );

        court1 = courtService.save(newCourt1);

        Assertions.assertEquals(1, courtService.findAll().size());

        Court response = courtService.findByName("Quadra 999");

        Assertions.assertNull(response);
    }

    @Test
    @Transactional
    @DisplayName("should return the detailed dto")
    void findCourtByIdSucess() {
        Assertions.assertEquals(0, courtService.findAll().size());

        Court court1 = new Court(
                "Nome",
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE,
                90L,
                6L
        );

        Court court2 = new Court(
                "Outro nome",
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE,
                90L,
                6L
        );

        court1 = courtService.save(court1);
        court2 = courtService.save(court2);

        Assertions.assertEquals(2, courtService.findAll().size());

        CourtDetailedResponseDto response = courtService.findCourtDetailedResponseDtoById(court1.getId());

        Assertions.assertEquals(court1.getName(), response.name());
        Assertions.assertNotEquals(courtService.findCourtDetailedResponseDtoById(court2.getId()).name(), court1.getName());
        Assertions.assertEquals(court1.getCourtAvailabilityStatusEnum(), response.courtAvailabilityStatusEnum());
        Assertions.assertEquals(court1.getMinimumIntervalBetweenReservation(), response.minimumIntervalBetweenReservation());
    }

    @Test
    @Transactional
    @DisplayName("must return null in case of absence of block")
    void findCourtByIdNotExists() {
        CourtDetailedResponseDto response = courtService.findCourtDetailedResponseDtoById(100L);

        Assertions.assertNull(response);
    }

    @Test
    @Transactional
    @DisplayName("display courts dto success")
    void findAllSucess() {
        Court court1 = new Court(
                "Nome",
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE,
                90L,
                6L
        );

        Court court2 = new Court(
                "Outro nome",
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE,
                90L,
                6L
        );

        Court court3 = new Court(
                "Name 3",
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE,
                90L,
                6L
        );

        court1 = courtService.save(court1);
        court2 = courtService.save(court2);
        court3 = courtService.save(court3);

        Assertions.assertEquals(3, courtService.findAll().size());

        Collection<CourtBasicResponseDto> response = courtService.findAllCourtBasicResponseDto();

        Assertions.assertEquals(3, response.size());

        List<CourtBasicResponseDto> values = response.stream().toList();

        Assertions.assertEquals(values.get(0).id(), court1.getId());
        Assertions.assertEquals(values.get(0).name(), court1.getName());
        Assertions.assertEquals(values.get(1).id(), court2.getId());
        Assertions.assertEquals(values.get(1).name(), court2.getName());
        Assertions.assertEquals(values.get(2).id(), court3.getId());
        Assertions.assertEquals(values.get(2).name(), court3.getName());
    }

    @Test
    @Transactional
    @DisplayName("must return null in case of absence of block")
    void findAllSucessNotExistsCourts() {
        Assertions.assertTrue(courtService.findAllCourtBasicResponseDto().isEmpty());
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
