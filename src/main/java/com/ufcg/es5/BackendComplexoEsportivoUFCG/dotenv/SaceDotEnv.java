package com.ufcg.es5.BackendComplexoEsportivoUFCG.dotenv;

import io.github.cdimascio.dotenv.Dotenv;

public class SaceDotEnv {
    private static Dotenv dotenv;

    public SaceDotEnv(){
        dotenv = Dotenv.configure().load();
    }

    public String getJWTSecret(){
        return dotenv.get("JWT_SECRET");
    }
}
