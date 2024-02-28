package com.ufcg.es5.BackendComplexoEsportivoUFCG.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.ValidacaoGeral;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.query.results.ResultBuilderEntityValued;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aluno")
@EqualsAndHashCode(callSuper = false)
public class Aluno extends Usuario {

    @JsonProperty("reserva")
    @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Reserva reserva;

    public boolean reservar(Reserva newReserva) {
        this.reserva = newReserva;
        return true;
    }

    public Reserva exibirReserva() {return this.reserva;}

    public boolean removerReserva() {
        this.reserva = null;
        return true;
    }

}
