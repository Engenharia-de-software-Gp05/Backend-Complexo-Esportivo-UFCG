package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = Court.COURT_TABLE)
public class Court extends BasicEntity {

    public static final String COURT_TABLE = "court";
    private static final String COURT_ID_COLUMN = "court_id";
    private static final String COURT_IMAGE_URL_TABLE = "court_image_url";
    private static final String NAME_COLUMN = "name";
    private static final String COURT_PROPERTY = "court";
    private static final String STATUS_COLUMN = "status";
    public static final String RESERVATION_DURATION_COLUMN = "reservation_duration";
    public static final String MINIMUM_INTERVAL_BETWEEN_RESERVATION_COLUMN = "minimum_interval_between_reservation";

    @Column(name = NAME_COLUMN, nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = COURT_IMAGE_URL_TABLE, joinColumns = @JoinColumn(name = COURT_ID_COLUMN))
    private List<String> imagesUrls = new ArrayList<>();

    @OneToMany(mappedBy = COURT_PROPERTY, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("court")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = COURT_PROPERTY, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UnavailableReservation> unavailableReservations = new ArrayList<>();

    @Column(name = STATUS_COLUMN, nullable = false)
    @Enumerated(EnumType.STRING)
    private CourtAvailabilityStatusEnum courtAvailabilityStatusEnum;

    @Column(name = RESERVATION_DURATION_COLUMN, nullable = false)
    private Long reservationDuration;

    @Column(name = MINIMUM_INTERVAL_BETWEEN_RESERVATION_COLUMN, nullable = false)
    private Long minimumIntervalBetweenReservation;

    public Court() {
    }

    public Court(String name, List<String> imagesUrls,
                 CourtAvailabilityStatusEnum status,
                 Long reservationDuration,
                 Long minimumIntervalBetweenReservation) {
        this.name = name;
        this.imagesUrls = imagesUrls;
        this.courtAvailabilityStatusEnum = status;
        this.reservationDuration = reservationDuration;
        this.minimumIntervalBetweenReservation = minimumIntervalBetweenReservation;
    }

    public void addImageUrl(String imageUrl) {
        this.imagesUrls.add(imageUrl);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservas) {
        this.reservations = reservas;
    }

    public CourtAvailabilityStatusEnum getCourtAvailabilityStatusEnum() {
        return courtAvailabilityStatusEnum;
    }

    public List<UnavailableReservation> getUnavailableReservations() {
        return unavailableReservations;
    }

    public void setUnavailableReservations(List<UnavailableReservation> unavailableReservations) {
        this.unavailableReservations = unavailableReservations;
    }

    public Long getReservationDuration() {
        return reservationDuration;
    }

    public void setReservationDuration(Long reservationDuration) {
        this.reservationDuration = reservationDuration;
    }

    public void setCourtAvailabilityStatusEnum(CourtAvailabilityStatusEnum courtStatusEnum) {
        this.courtAvailabilityStatusEnum = courtStatusEnum;
    }

    public Long getMinimumIntervalBetweenReservation() {
        return minimumIntervalBetweenReservation;
    }

    public void setMinimumIntervalBetweenReservation(Long minimumIntervalBetweenReservation) {
        this.minimumIntervalBetweenReservation = minimumIntervalBetweenReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Court court = (Court) o;
        return Objects.equals(name, court.name) && Objects.equals(imagesUrls, court.imagesUrls) && Objects.equals(reservations, court.reservations) && Objects.equals(unavailableReservations, court.unavailableReservations) && courtAvailabilityStatusEnum == court.courtAvailabilityStatusEnum && Objects.equals(reservationDuration, court.reservationDuration) && Objects.equals(minimumIntervalBetweenReservation, court.minimumIntervalBetweenReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, imagesUrls, reservations, unavailableReservations, courtAvailabilityStatusEnum, reservationDuration, minimumIntervalBetweenReservation);
    }

    @Override
    public String toString() {
        return "Court{" +
                "name='" + name + '\'' +
                ", imagesUrls=" + imagesUrls +
                ", reservations=" + reservations +
                ", unavailableReservations=" + unavailableReservations +
                ", courtAvailabilityStatusEnum=" + courtAvailabilityStatusEnum +
                ", reservationDuration=" + reservationDuration +
                ", minimumIntervalBetweenReservation=" + minimumIntervalBetweenReservation +
                '}';
    }
}
