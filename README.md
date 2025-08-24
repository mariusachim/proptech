# Proptech Application

This is a Spring Boot application for property management.

## Running with Docker

### Prerequisites

- Docker
- Docker Compose (optional)

### Building and Running the Application

#### Using Docker

1. Build the Docker image:
   ```bash
   docker build -t proptech .
   ```

2. Run the Docker container:
   ```bash
   docker run -p 8080:8080 proptech
   ```

#### Using Docker Compose

1. Build and run the application:
   ```bash
   docker-compose up
   ```

2. To run in detached mode:
   ```bash
   docker-compose up -d
   ```

3. To stop the application:
   ```bash
   docker-compose down
   ```

### Accessing the Application

Once the application is running, you can access it at:
- http://localhost:8080

The application also exposes an Actuator health endpoint at:
- http://localhost:8080/actuator/health

## Development

### Building the Application Locally

```bash
./gradlew build
```

### Running the Application Locally

```bash
./gradlew bootRun
```