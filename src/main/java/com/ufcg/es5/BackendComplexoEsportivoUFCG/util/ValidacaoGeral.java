package com.ufcg.es5.BackendComplexoEsportivoUFCG.util;

public class ValidacaoGeral {
    public static boolean validarId(Long id) {return id != null && id > 0;}

    public static boolean isNotEmptyOrNull(String string) {return string != null && !string.trim().isEmpty();}

    public static boolean isEmptyOrNull(String string) {return string == null || string.trim().isEmpty();}

    public static boolean isNotNull(Object object) {return object != null;}

    public static boolean isNull(Object object) {return object == null;}
}
