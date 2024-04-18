package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.db;


public record DbConnectionOptions(
        String dbUrl,
        String dbUser,
        String dbPassword,
        int connectionTimeout
) {

}