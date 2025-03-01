# Kafka ChatService Demo

This project is a demo for implementing a simple ChatService application with Kafka + SpringBoot + MongoDB. The main goal is to demonstrate the usage of Kafka and WebSockets.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Docker Setup](#docker-setup)
- [Endpoints](#endpoints)
- [Example Requests](#example-requests)
- [Security Considerations](#security-considerations)
- [License](#license)

## Features

- Send and receive messages in real-time using WebSockets.
- Save chat history in MongoDB.
- Use Kafka to broadcast messages to all subscribers.

## Technologies Used

- **Backend**: Java, Spring Boot, MongoDB, Kafka, WebSockets, HTML/CSS/JS
- **Tools**: IntelliJ IDEA, Maven, Postman for API testing, Docker

## Setup Instructions

### Prerequisites

1. Ensure you have the following installed (or Docker and docker-compose):
    - Java 17
    - MongoDB
    - Maven
    - Kafka
2. Clone the repository and navigate to the project folder.

```bash
git clone https://github.com/RossiJr/JavaDemos/tree/main
cd JavaDemos/Kakfa-ChatService
```

### Backend Setup

1. Build and run the Spring Boot backend:

   ```bash
   mvn clean package -DskipTests
   mvn spring-boot:run
   ```

## Docker Setup

1. Ensure Docker and Docker Compose are installed on your system.

2. Build and start the application using Docker Compose (ensure the application is already built):

   ```bash
   docker-compose up --build
   ```

3. The application should now be running, with the backend accessible at `http://localhost:8080` and the database
   running in a Docker container.

4. To stop the containers:

   ```bash
   docker-compose down
   ```

## Endpoints

### Chat Service

- `POST /api/v1/chat/send`: Send a message
- `GET /api/v1/chat/history`: Get chat history

## Example Requests

### Send Message

#### Request

- **Endpoint**: `POST /api/v1/chat/send?sender=<senderName>&message=<message>`

#### Response

- HTTP Status: 200 OK

### Message History

#### Request

- **Endpoint**: `POST /api/v1/chat/history`

#### Response

- HTTP Status: 200 OK
  ```json
  [
    {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "sender": "Alice",
      "message": "Hello, Bob!",
      "timestamp": 1740857724731
    },
    {
      "id": "123e4567-e89b-12d3-a456-426614174001",
      "sender": "Bob",
      "message": "Hi, Alice!",
      "timestamp": 1740786493363
    }
  ]
  ```

## Security Considerations

- As it is not the focus of this demo, the following security considerations are not implemented:
  - Authentication and authorization mechanisms.
  - Input validation and sanitization.
  - Secure configuration management.
  - Secure communication between services.
  - Secure storage of sensitive data.
  - Secure session management.
  - Secure access control.
  - Secure API design.
  - Secure data handling and processing.
- Always develop applications with security in mind, following the best practices and guidelines for secure software development.
- Again, this application **only intends** to demonstrate the usage of Kafka and WebSockets, not security best practices that might be related to it.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
