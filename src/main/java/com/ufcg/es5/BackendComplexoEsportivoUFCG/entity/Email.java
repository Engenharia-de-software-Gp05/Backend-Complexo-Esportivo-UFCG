package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email")
public class Email extends BasicEntity {

    private static final String USUARIO_ID_COLUMN = "usuario_id";
    private static final String EMAIL_ID_COLUMN = "email_id";

    @JsonProperty("email")
    @Column(name = "email",nullable = false)
    private String email;

    @JsonProperty("usuario")
    @OneToOne
    @JoinColumn(name = USUARIO_ID_COLUMN, referencedColumnName = EMAIL_ID_COLUMN)
    private Usuario usuario;
}
