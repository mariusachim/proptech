# Build stage
FROM gradle:8.14.3-jdk-21-and-24 AS build
WORKDIR /app

# Cache-friendly: copy Gradle files first
COPY gradle gradle
COPY gradlew .
COPY settings.gradle* .
COPY build.gradle* .

# Warm Gradle caches between runs using BuildKit cache mounts
RUN --mount=type=cache,target=/home/gradle/.gradle/caches \
    --mount=type=cache,target=/home/gradle/.gradle/wrapper \
    ./gradlew --no-daemon build -x test || true

# Now copy sources
COPY src src

# Real build (often skip tests here; run them in a separate job)
RUN --mount=type=cache,target=/home/gradle/.gradle/caches \
    --mount=type=cache,target=/home/gradle/.gradle/wrapper \
    ./gradlew --no-daemon clean build

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/proptech-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
