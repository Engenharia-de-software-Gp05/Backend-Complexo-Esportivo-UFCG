package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmailFormat {

    public static class Serializer extends JsonSerializer<String> {
        @Override
        public void serialize(String email, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (validateEmail(email)) {
                jsonGenerator.writeString(email);
            } else {
                throw new IllegalArgumentException("Invalid email");
            }
        }
    }

    public static class Deserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String email = jsonParser.getValueAsString();
            if (validateEmail(email)) {
                return email;
            } else {
                throw new IllegalArgumentException("Invalid email");
            }
        }
    }

    public static boolean validateEmail(String email) {
        return email.split("@")[1].equals("estudante.ufcg.edu.br");
    }

}
