package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.enums.ReservationAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserAccountStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE);
public class GetTest {
    
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
    private static final String COURT_NAME_2 = "Volleyball2 Court2";
    private static final String COURT_IMAGE_URL = "imageurl.com";

    private static SaceUser user1;
    private static SaceUser user2;
    private static Court court;
    private static Court court2;
    Reservation reservation1User1;
    Reservation reservation2User1;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;    

    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private SaceUserService userService;

    @BeforeEach
    public void setup(){
        createDateTimes();
        createUsers();  
        createCourts();
        createReservations();
    }

    @Test
    public void teste1(){
        assertEquals(0, 0);
    }
    
    @Test
    public void getByuserIdShouldReturnSucess() {
        List<ReservationResponseDto> reservations = (List<ReservationResponseDto>) reservationService.findByUserId(user1.getId());

        Assertions.assertNotNull(reservations);
        Assertions.assertEquals(2, reservations.size());
        // tests if the reservations are the same
        ReservationResponseDto reservation1 = reservations.get(0);
        ReservationResponseDto reservation2 = reservations.get(1);
        Assertions.assertEquals(reservation1User1, reservation1);
        Assertions.assertEquals(reservation2User1, reservation2);
        
    }

    @Test
    public void getByUserIdWithUnexistentUserShouldReturnEmptyList() {
        List<ReservationResponseDto> reservations = (List<ReservationResponseDto>) reservationService.findByUserId(99999L);

        Assertions.assertEquals(0, reservations.size());
    }

    @Test
    public void getByCourtIdUserIdAndDateTimeRageShouldReturnSucess() {
        List<ReservationResponseDto> reservations = (List<ReservationResponseDto>) reservationService.findByCourtUserIdAndDateTimeRange(startDateTime, endDateTime, court.getId(), user1.getId());

        Assertions.assertNotNull(reservations);
        Assertions.assertEquals(1, reservations.size());
        ReservationResponseDto reservation = reservations.get(0);
        Assertions.assertEquals(reservation1User1, reservation);
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

    private void createCourts() {
        court = new Court(
                COURT_NAME,
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE,
                3L
        );
        court = courtService.save(court);

        court2 = new Court(
                COURT_NAME_2,
                List.of(COURT_IMAGE_URL),
                CourtAvailabilityStatusEnum.AVAILABLE,
                3L
        );
        court2 = courtService.save(court2);

    }

    private void createDateTimes(){
        startDateTime = LocalDateTime.of(2024, 1, 1, 10, 0);
        endDateTime = startDateTime.plusHours(2L);
    }

    private void createReservations(){
        reservation1User1 = new Reservation(
            startDateTime,
            endDateTime,
            court, 
            user1, 
            ReservationAvailabilityStatusEnum.BOOKED
            );
        reservationService.save(reservation1User1);

        reservation2User1 = new Reservation(
            startDateTime,
            endDateTime,
            court2, 
            user1, 
            ReservationAvailabilityStatusEnum.BOOKED
            );
            reservationService.save(reservation2User1);
                
    }
}
