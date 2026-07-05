# =====================================================================
# STAGE 1: COMPILATION & BUILD PIPELINE (Java 21 Maven Build Space)
# =====================================================================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy dependency mappings and project structure into the container space
COPY pom.xml .
COPY src ./src

# Compile and package the application context into an executable JAR artifact
RUN mvn clean package -DskipTests

# =====================================================================
# STAGE 2: RUNTIME RUNNER ENVIRONMENT (Ultra-lightweight Temurin JDK 21)
# =====================================================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Safely extract the compiled executable .jar file from the build layer stage
COPY --from=build /app/target/*.jar app.jar

# Execute the application binary with production configuration parameters
ENTRYPOINT ["java", "-jar", "app.jar"]
