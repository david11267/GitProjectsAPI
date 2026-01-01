# ===========================
# Stage 1: Build
# ===========================
# Use a valid Maven 3.9.12 image with Eclipse Temurin JDK 21
FROM maven:3.9.12-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only pom.xml first for caching
COPY pom.xml .

# Download dependencies offline
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# ===========================
# Stage 2: Runtime
# ===========================
# Use a small JRE image that supports Java 21
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Add non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
