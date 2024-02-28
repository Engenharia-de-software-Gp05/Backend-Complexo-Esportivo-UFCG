package com.ufcg.es5.BackendComplexoEsportivoUFCG.util;

public class ValidacaoUsuario {

    public static boolean validarTamanho(String email) {return email.split("@").length == 2;}

    public static boolean validarEmail(String email) {
        return verificarConteudo(email) &&
                validarTamanho(email) &&
                email.split("@")[1].equals("estudante.ufcg.edu.br");
    }

    private static boolean verificarConteudo(String email) {return ValidacaoGeral.isNotEmptyOrNull(email);}

}
