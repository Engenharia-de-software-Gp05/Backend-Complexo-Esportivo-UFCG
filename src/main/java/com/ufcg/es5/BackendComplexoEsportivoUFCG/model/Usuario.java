package com.ufcg.es5.BackendComplexoEsportivoUFCG.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.ValidacaoGeral;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @Column(nullable = false)
    private String nome;

    @JsonProperty("email")
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Email email;

    @JsonProperty("nTelefone")
    @Column(nullable = false)
    private String nTelefone;

    @JsonProperty("senha")
    @Column(nullable = false)
    private String senha;

    public boolean logar(String email, String senha) {
        return this.email.getElmailTo().equals(email) && this.senha.equals(senha);
    }

    public boolean atualizarNome(String newNome) {
        if (validarEntradaString(newNome)) {return false;}
        this.nome = newNome;
        return true;
    }

    public boolean atualizarSenha(String newSenha) {
        if (validarEntradaString(newSenha)) {return false;}
        this.senha = newSenha;
        return true;
    }

    public boolean atualizarNTelefone(String newNTelefone) {
        if (validarEntradaString(newNTelefone)) {return false;}
        this.nTelefone = newNTelefone;
        return true;
    }

    private boolean validarEntradaString(String str) {return ValidacaoGeral.isEmptyOrNull(str);}

}
