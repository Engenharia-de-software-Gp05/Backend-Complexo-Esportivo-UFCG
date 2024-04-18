package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.health;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.db.DbConnectionOptions;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInternalErrorServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

@Service
public class HealthService {

    private final DbConnectionOptions dbConnectionOptions;

    @Autowired
    public HealthService(DbConnectionOptions dbConnectionOptions) {
        this.dbConnectionOptions = dbConnectionOptions;
    }

    public void healthCheck() {
        try {
            this.checkDbConnection();
        } catch (Exception e) {
            throw new SaceInternalErrorServerException(e.getMessage());
        }
    }

    private void checkDbConnection() {
        try (Connection dbConnection = DriverManager.getConnection(dbConnectionOptions.dbUrl(), dbConnectionOptions.dbUser(), dbConnectionOptions.dbPassword())) {
            if (!dbConnection.isValid(dbConnectionOptions.connectionTimeout())) {
                throw new SaceInternalErrorServerException("Db problem!");
            }
        } catch (Exception e) {
            throw new SaceInternalErrorServerException("Db problem!");
        }
    }
}

