FROM gradle:8.14-jdk21 AS build

WORKDIR /app

COPY build.gradle .

RUN gradle wrapper --gradle-version 8.14

COPY src ./src

RUN ./gradlew build -x test -B

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/blackjack-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]