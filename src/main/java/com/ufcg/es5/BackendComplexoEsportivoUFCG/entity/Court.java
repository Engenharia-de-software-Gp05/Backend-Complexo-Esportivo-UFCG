package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

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
    private static final String MINIMUM_TIME_FOR_OTHER_RESERVATION = "min_time_for_other_reservation";

    @Column(name = NAME_COLUMN, nullable = false)
    private String name;

    @Column(name = MINIMUM_TIME_FOR_OTHER_RESERVATION, nullable = false)
    private Long minimumTimeForOtherReservation;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = COURT_IMAGE_URL_TABLE, joinColumns = @JoinColumn(name = COURT_ID_COLUMN))
    private List<String> imagesUrls = new ArrayList<>();

    @OneToMany(mappedBy = COURT_PROPERTY, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @Column(name = STATUS_COLUMN, nullable = false)
    @Enumerated(EnumType.STRING)
    private CourtAvailabilityStatusEnum courtAvailabilityStatusEnum;

    public Court() {
    }

    public Court(String name, List<String> imagesUrls) {
        this.name = name;
        this.imagesUrls = imagesUrls;
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

    public void setCourtAvailabilityStatusEnum(CourtAvailabilityStatusEnum courtStatusEnum) {
        this.courtAvailabilityStatusEnum = courtStatusEnum;
    }

    public Long getMinimumTimeForOtherReservation() {
        return this.minimumTimeForOtherReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Court court = (Court) o;
        return Objects.equals(name, court.name) && Objects.equals(imagesUrls, court.imagesUrls) && Objects.equals(reservations, court.reservations) && courtAvailabilityStatusEnum == court.courtAvailabilityStatusEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, imagesUrls, reservations, courtAvailabilityStatusEnum);
    }

    @Override
    public String toString() {
        return "Court{" +
                "name='" + name + '\'' +
                ", imagesUrls=" + imagesUrls +
                ", reservas=" + reservations +
                ", courtStatusEnum=" + courtAvailabilityStatusEnum +
                '}';
    }
}
