package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${db.connection.timeout}")
    private int connectionTimeout;

    @Bean
    public DbConnectionOptions dbConnectionOptions() {
        return new DbConnectionOptions(dbUrl, dbUser, dbPassword, connectionTimeout);
    }
}