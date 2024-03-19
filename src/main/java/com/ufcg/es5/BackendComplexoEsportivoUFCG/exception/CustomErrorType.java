package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorType {
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    public CustomErrorType(SystemInternalException e) {
        this.timestamp = LocalDateTime.now();
        this.message = e.getMessage();
        this.errors = new ArrayList<>();
    }

    public CustomErrorType(LocalDateTime localDateTime, String message) {
        this.timestamp = localDateTime;
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return this.errors;
    }

}
