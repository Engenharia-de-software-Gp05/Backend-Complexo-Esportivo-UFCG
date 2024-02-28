package com.ufcg.es5.BackendComplexoEsportivoUFCG.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reserva")
public class Reserva {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @DateTimeFormat
    @JsonProperty("dataInicio")
    @Column(nullable = false)
    private String dataInicio;

    @DateTimeFormat
    @JsonProperty("dataFinal")
    @Column(nullable = false)
    private String dataFinal;

    @JsonProperty("centroEsp")
    @ManyToOne
    @JoinColumn(name = "centro_Esportivo_id", referencedColumnName = "id")
    private CentroEsportivo centroEsp;

    @JsonProperty("aluno")
    @OneToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    private Aluno aluno;

    public boolean isDataInicio(String dataInicio) {return this.dataInicio.equals(dataInicio);}
}
