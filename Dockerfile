# Multi-stage build for Spring Boot application
# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8081

# Environment variables (can be overridden at runtime)
ENV SPRING_PROFILES_ACTIVE=docker
ENV DB_URL=jdbc:mysql://mysql-libros:3306/sisdb2025
ENV DB_USERNAME=root
ENV DB_PASSWORD=abcd
ENV SERVER_PORT=8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
