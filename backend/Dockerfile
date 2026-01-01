# ===========================
# Stage 1: Build
# ===========================
FROM maven:3.9.11-eclipse-temurin-21-jdk AS builder

WORKDIR /app

# Copy Maven wrapper if you use it (optional)
# COPY mvnw .
# COPY .mvn/ .mvn
# RUN chmod +x mvnw

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .

# Download dependencies offline
RUN mvn dependency:go-offline -B

# Copy source code and build JAR (skip tests for faster builds)
COPY src ./src
RUN mvn clean package -DskipTests -B

# ===========================
# Stage 2: Runtime
# ===========================
FROM eclipse-temurin:21-jdk-alpine

# Set a lightweight non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
