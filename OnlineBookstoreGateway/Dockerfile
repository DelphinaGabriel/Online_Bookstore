# Stage 1: Build
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Download dependencies only (faster builds)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application without running tests
RUN mvn clean package -DskipTests -B

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 9000

# Use exec form for ENTRYPOINT
ENTRYPOINT ["java", "-jar", "app.jar"]
