package com.ufcg.es5.BackendComplexoEsportivoUFCG;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "Authorization", in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "SACE back-end", version = "v1.0.0"), security = {@SecurityRequirement(name = "Authorization")})
public class BackendComplexoEsportivoUfcgApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendComplexoEsportivoUfcgApplication.class, args);
    }
}
