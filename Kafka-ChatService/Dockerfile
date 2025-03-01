FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Kafka-ChatService-0.0.1-SNAPSHOT.jar chat-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "chat-app.jar"]
