package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailFormat;

public record UserSaveDto(
        String name,
        @JsonSerialize(using = EmailFormat.Serializer.class)
        @JsonDeserialize(using = EmailFormat.Deserializer.class)
        String email,
        String phoneNumber,
        String password
) {
}
