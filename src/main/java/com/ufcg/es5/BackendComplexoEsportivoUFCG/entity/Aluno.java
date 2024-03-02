package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "aluno")
public class Aluno extends BasicEntity {

    private static final String NAME_COLUMN = "NAME";
    private static final String ALUNO_PROPERTY = "aluno";

    @Column(name = NAME_COLUMN, nullable = false)
    String name;

    @OneToMany(mappedBy = ALUNO_PROPERTY, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reserva> reservas;

    public Aluno() {
    }

    public Aluno(String name){
        this.name = name;
    }

}
