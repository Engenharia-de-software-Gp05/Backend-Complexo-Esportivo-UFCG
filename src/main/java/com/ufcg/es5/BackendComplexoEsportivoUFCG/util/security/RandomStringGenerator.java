package com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security;

import org.apache.commons.lang3.RandomStringUtils;


public class RandomStringGenerator {

    private static final String ALPHA_NUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String ALPHA_NUMERIC_CHARACTERS_INCLUDING_SPECIAL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%&";

    private RandomStringGenerator(){}

    public static String random(int size, String characters){
        return RandomStringUtils.random( size, characters);
    }

    public static String randomIncludingSpecialCharacters(int size){
        return random(size, ALPHA_NUMERIC_CHARACTERS_INCLUDING_SPECIAL_CHARACTERS);
    }

    public static String randomAlphaNumeric(int size){
        return random(size, ALPHA_NUMERIC_CHARACTERS);
    }
}
