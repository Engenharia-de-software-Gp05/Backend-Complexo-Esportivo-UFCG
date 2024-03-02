package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class Reserva extends BasicEntity {

    private static final String START_DATE_TIME_COLUMN = "star_date_time";
    private static final String END_DATE_TIME_COLUMN = "end_date_time";
    private static final String CENTRO_ESPORTIVO_ID_COLUMN = "centro_esportivo_id";
    private static final String ALUNO_ID_COLUMN = "aluno_id";

    @Column(name = START_DATE_TIME_COLUMN, nullable = false)
    private LocalDateTime startDateTime;
    @Column(name = END_DATE_TIME_COLUMN, nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = CENTRO_ESPORTIVO_ID_COLUMN, nullable = false)
    private CentroEsportivo centroEsportivo;

    @ManyToOne
    @JoinColumn(name = ALUNO_ID_COLUMN, nullable = false)
    private Aluno aluno;

}
