# Build stage
FROM gradle:8.14.3-jdk-21-and-24 AS build
WORKDIR /app

# 1) Copy only files that affect dependency resolution to maximize layer cache hits
COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle* .
COPY build.gradle* .

# Make wrapper executable and normalize line endings (helps on macOS/Windows)
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# 2) Warm Gradle caches (wrapper + dependencies) using BuildKit cache mounts
#    - /root/.gradle is the Gradle user home
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon help

# 3) Now bring in the rest of the sources
COPY src src

# 4) Build the runnable JAR (avoid 'clean' in CI to keep caches hot)
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon bootJar

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/proptech-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
