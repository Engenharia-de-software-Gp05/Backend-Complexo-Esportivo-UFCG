FROM gradle:jdk20-alpine AS BUILD

WORKDIR /app

COPY . .

RUN ./gradlew build -x test

FROM amazoncorretto:20.0.2

WORKDIR /app

COPY --from=BUILD /app/build/libs/Backend-Complexo-Esportivo-UFCG-1.0.0.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar", "Backend-Complexo-Esportivo-UFCG-1.0.0.jar"]