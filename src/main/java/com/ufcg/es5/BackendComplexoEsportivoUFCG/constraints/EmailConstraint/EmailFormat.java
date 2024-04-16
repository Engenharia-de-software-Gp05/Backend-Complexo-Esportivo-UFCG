package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.UtilConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;

import java.io.IOException;

public class EmailFormat {

    public static class Serializer extends JsonSerializer<String> {
        @Override
        public void serialize(String email, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            validateEmail(email);
            jsonGenerator.writeString(email);
        }
    }

    public static class Deserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String email = jsonParser.getValueAsString();
            validateEmail(email);
            return email;
        }
    }
    private static void validateEmail(String email) {
        if (!validateEmailLogical(email)) {
            throw new SaceInvalidArgumentException("Invalid username");
        }
    }

    public static boolean validateEmailLogical(String email) {
        String regex = "^[A-Za-z0-9_.-]+@[A-Za-z0-9-.]+\\.[A-Za-z.]+$";
        return UtilConstraint.isNotBlank(email) && !email.contains(" ") && email.matches(regex) &&
                email.indexOf('@') < email.lastIndexOf('.');
    }
}
