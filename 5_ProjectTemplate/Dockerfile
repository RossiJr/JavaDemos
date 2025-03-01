# Dockerfile for Spring Boot
FROM openjdk:17-jdk-slim

# Sets the working directory in the container (where the application's code will be)
WORKDIR /app

# Copies the JAR file from the target folder (where the application is built) to the container
COPY target/*.jar app.jar

# Exposes the port your Spring Boot app runs on
EXPOSE 8080

# Runs the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]