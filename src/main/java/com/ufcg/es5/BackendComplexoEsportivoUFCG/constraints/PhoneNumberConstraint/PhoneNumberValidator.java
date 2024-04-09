package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

    private final Set<String> DDDS = new HashSet<>(Set.of(
            "61", "62", "64", "65", "66", "67",
            "82", "71", "73", "74", "75", "77", "85", "88", "98", "99",
            "83", "81", "87", "86", "89", "84", "79",
            "68", "96", "92", "97", "91", "93", "94", "69", "95", "63",
            "27", "28", "31", "32", "33", "34", "35", "37", "38",
            "21", "22", "24", "11", "12", "13", "14", "15", "16",
            "17", "18", "19",
            "41", "42", "43", "44", "45", "46", "51", "53", "54", "55",
            "47", "48", "49"
    ));

    @Override
    public boolean isValid(
            String phoneNumber,
            ConstraintValidatorContext context
    ) {
        if (phoneNumber == null) return false;

        phoneNumber = phoneNumber.replace(" ", "");
        phoneNumber = phoneNumber.replace("(", "");
        phoneNumber = phoneNumber.replace(")", "");
        phoneNumber = phoneNumber.replace("-", "");

        if (phoneNumber.length() != 11) return false;

        String ddd = phoneNumber.substring(0, 2);
        if (!DDDS.contains(ddd)) return false;

        if (phoneNumber.charAt(2) != '9') return false;

        for (int i = 3; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) return false;
        }

        return true;
    }
}
