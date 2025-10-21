# ========== BUILD STAGE ==========
FROM maven:3.9.11-eclipse-temurin-25 AS builder

WORKDIR /app

# Copy pom.xml first and download dependencies (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ========== RUNTIME STAGE ==========
FROM eclipse-temurin:25-jre-alpine

# Set a lightweight non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
