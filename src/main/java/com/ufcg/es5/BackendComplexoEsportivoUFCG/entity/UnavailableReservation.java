package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = UnavailableReservation.UNAVAILABLE_RESERVATION_TABLE)
public class UnavailableReservation extends BasicEntity {

    public static final String UNAVAILABLE_RESERVATION_TABLE = "unavailable_reservation";
    private static final String START_DATE_TIME_COLUMN = "start_date_time";
    private static final String END_DATE_TIME_COLUMN = "end_date_time";
    private static final String COURT_ID_COLUMN = "court_id";

    @Column(name = START_DATE_TIME_COLUMN, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @Column(name = END_DATE_TIME_COLUMN, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = COURT_ID_COLUMN, nullable = false)
    private Court court;

    public UnavailableReservation() {
    }

    public UnavailableReservation(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Court court
    ) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.court = court;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UnavailableReservation that = (UnavailableReservation) o;
        return Objects.equals(startDateTime, that.startDateTime) && Objects.equals(endDateTime, that.endDateTime) && Objects.equals(court, that.court);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDateTime, endDateTime, court);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", court=" + court +
                '}';
    }
}
