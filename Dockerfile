# Build stage
FROM gradle:8.14.3-jdk-21-and-24 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/proptech-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]