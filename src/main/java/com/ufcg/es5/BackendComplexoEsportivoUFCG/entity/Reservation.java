package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name =   Reservation.RESERVATION_TABLE)
public class Reservation extends BasicEntity {

    private static final String START_DATE_TIME_COLUMN = "start_date_time";
    private static final String END_DATE_TIME_COLUMN = "end_date_time";
    private static final String COURT_ID_COLUMN = "court_id";
    private static final String USER_ID_COLUMN = "user_id";
    public static final String RESERVATION_TABLE = "reservation";


    @Column(name = START_DATE_TIME_COLUMN, nullable = false)
    private LocalDateTime startDateTime;
    @Column(name = END_DATE_TIME_COLUMN, nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = COURT_ID_COLUMN, nullable = false)
    private Court court;

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN, nullable = false)
    private User user;

    public Reservation(){
    }

    public Reservation(LocalDateTime startDateTime, LocalDateTime endDateTime, Court court, User user){
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.court = court;
        this.user = user;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(startDateTime, that.startDateTime) && Objects.equals(endDateTime, that.endDateTime) && Objects.equals(court, that.court) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDateTime, endDateTime, court, user);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", court=" + court +
                ", user=" + user +
                '}';
    }
}
