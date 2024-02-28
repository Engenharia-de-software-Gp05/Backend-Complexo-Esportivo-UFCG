package com.ufcg.es5.BackendComplexoEsportivoUFCG.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.ValidacaoGeral;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "centro_esportivo")
public class CentroEsportivo {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @Column(nullable = false)
    private String nome;

    @JsonProperty("tipoAmbiente")
    @Column(nullable = false)
    private String tipoAmbiente;

    @JsonProperty("imagem")
    @Column
    private String imagem;

    @JsonProperty("reservas")
    @Builder.Default
    @OneToMany(mappedBy = "centroEsp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public boolean adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        return true;
    }

    public List<Reserva> exibirReservas() {return this.reservas;}

    public Reserva exibirReserva(String dataInicio) {
        for (Reserva reserva : this.reservas) {
            if (reserva.isDataInicio(dataInicio)) return reserva;
        }
        return null;
    }

    public boolean removerReserva(Aluno aluno) {
        if (ValidacaoGeral.isNull(aluno) || ValidacaoGeral.isNull(aluno.getReserva())) return false;
        this.reservas.remove(aluno.getReserva());
        return true;
    }

    public boolean removerReserva(String dataInicio) {
        if (ValidacaoGeral.isEmptyOrNull(dataInicio)) return false;

        Reserva reserva = null;
        for (Reserva r : this.reservas) {
            if (r.isDataInicio(dataInicio)) {
                reserva = r;
                break;
            }
        }
        if (reserva != null) {
            this.reservas.remove(reserva);
            return true;
        }
        return false;
    }

}
