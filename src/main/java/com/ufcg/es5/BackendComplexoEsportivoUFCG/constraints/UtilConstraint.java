package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints;

public class UtilConstraint {
    public static boolean isNotBlank(String str) {
        return str != null && !str.replaceAll("\\s", "").isEmpty();
    }
}
