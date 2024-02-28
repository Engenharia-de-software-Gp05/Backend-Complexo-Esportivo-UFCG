package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.UsuarioDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.model.Email;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAlunoPostRequestDTO {

    @JsonProperty("nome")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @JsonProperty("email")
    @NotBlank(message = "Email é obrigatório")
    @EmailConstraint
    private Email email;

    @JsonProperty("nTelefone")
    @NotBlank(message = "Número não pode ser vazio")
    @Pattern(regexp="[0-9]+", message="O número de telefone deve conter apenas dígitos")
    @Size(min = 9, max = 9, message = "O número de telefone deve ter exatamente 9 dígitos, 2 para DD, um dígito 9 obrigatório e os 6 números de sua operadora")
    private String nTelefone;

    @JsonProperty("senha")
    @NotBlank(message = "Tem de haver uma senha")
    private String senha;

}
