package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.ValidacaoGeral;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "centro_esportivo")
public class CentroEsportivo extends BasicEntity {

    private  static final String CENTRO_ESPORTIVO_ID_COLUMN = "centro_esportivo_id";
    private static final String CENTRO_ESPORTIVO_IMAGE_URL_TABLE = "centro_esportivo_image_url";

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = CENTRO_ESPORTIVO_IMAGE_URL_TABLE, joinColumns = @JoinColumn(name = CENTRO_ESPORTIVO_ID_COLUMN))
    private List<String> imagesUrls = new ArrayList<>();

    @OneToMany(mappedBy = "centroEsp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public CentroEsportivo(){
    }

    public CentroEsportivo(String name, List<String> imagesUrls){
        this.name = name;
        this.imagesUrls = imagesUrls;
    }

    public void addImageUrl(String imageUrl){
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

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CentroEsportivo that = (CentroEsportivo) o;
        return Objects.equals(name, that.name) && Objects.equals(imagesUrls, that.imagesUrls) && Objects.equals(reservas, that.reservas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, imagesUrls, reservas);
    }
}
