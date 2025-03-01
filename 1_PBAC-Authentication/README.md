# PBAC-Authentication Demo

This project is a demo for implementing Permission-Based Access Control (PBAC) authentication system in a Java + Spring Boot application.

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

- User authentication and authorization.
- JWT (JSON Web Token) for secure token-based authentication.
- Permission-based access control using permissions and roles to *group permissions*.
- RESTful APIs for authentication and user management.
- Users can only access their own data.

## Technologies Used

- **Backend**: Java, Spring Boot, Spring Security, JWT, PostgreSQL
- **Tools**: IntelliJ IDEA, Maven, Postman for API testing, Docker

## Setup Instructions

### Prerequisites

1. Ensure you have the following installed (or Docker and docker-compose):
    - Java 17
    - PostgreSQL
    - Maven
2. Clone the repository and navigate to the project folder.

```bash
git clone https://github.com/RossiJr/JavaDemos/tree/main
cd JavaDemos/1_PBAC-Authentication
```

### Backend Setup

1. Configure PostgreSQL:
    - Create a database `Authentication`.
    - Use environment variables to set database credentials instead of hardcoding them in the `application.properties`
      file.

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/Authentication
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}
   ```

    - Set the environment variables `DB_USERNAME` and `DB_PASSWORD` in your system or container.

2. Build and run the Spring Boot backend:

   ```bash
   mvn clean install -DskipTests
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

### Authentication

- `POST /api/v1/authentication/login`: User login
- `POST /api/v1/authentication/validate`: Validate Token

### User Management

- `GET /api/v1/user`: Retrieve user details (each user can access only their own data)
- `POST /api/v1/user`: Create a user (admin only)

### Role-Based Access Control Demonstration

- `GET /api/v1/health/user`: Public health check endpoint (accessible to `VIEW_USER` permission).
- `GET /api/v1/health/admin`: Admin health check endpoint (accessible to `HEALTH_CHECK` permission).

## Example Requests

### Authenticate

#### Request

- **Endpoint**: `POST /api/v1/authentication/login`
- **Body**:
  ```json
  {
    "email": "user@example.com",
    "password": "securepassword"
  }
  ```

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "token": "<jwt-token>",
    "userId": "<user-id>"
  }
  ```

### Validate Token

#### Request

- **Endpoint**: `POST /api/v1/authentication/validate`
- **Body**:
  ```json
  {
    "token": "<jwt-token>"
  }
  ```

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "Token is valid"
  }
  ```

### Retrieve User Details

#### Request

- **Endpoint**: `GET /api/v1/user`
- **Headers**:
  ```
  Authorization: Bearer <jwt-token>
  ```

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "email": "user@example.com",
    "createdAt": "2025-01-08T12:16:22.4063157-03:00",
    "updatedAt": "2025-01-08T12:16:22.4063157-03:00",
    "roles": [
        {
            "id": 2,
            "name": "ROLE_USER",
            "description": "Regular user role with limited permissions"
        }
    ]
  }
  ```

### Create User

#### Request

- **Endpoint**: `POST /api/v1/user`
- **Headers**:
  ```
  Authorization: Bearer <admin-jwt-token>
  ```
- **Body**:
  ```json
  {
    "email": "newuser@example.com",
    "password": "securepassword"
  }
  ```

#### Response

- HTTP Status: 201 CREATED
  ```json
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "email": "newuser@example.com",
    "createdAt": "2025-01-08T12:16:22.4063157-03:00"
  }
  ```

### Health Check for Users

#### Request

- **Endpoint**: `GET /api/v1/health/user`
- **Headers**:
  ```
  Authorization: Bearer <jwt-token>
  ```

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "message": "Public Health Check: Server is running and accessible to USER role."
  }
  ```

### Health Check for Admins

#### Request

- **Endpoint**: `GET /api/v1/health/admin`
- **Headers**:
  ```
  Authorization: Bearer <admin-jwt-token>
  ```

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "message": "Admin Health Check: Server is running and accessible to ADMIN role."
  }
  ```

## Security Considerations

- Use the correct Build-Lifecycle from Maven (in this project we are skipping many phases and generating the JAR without testing, as it is a demo designed to local development only).
- Use HTTPS in production to secure API communications.
- Store JWT securely on the client side (e.g., in HTTP-only cookies or local storage).
- Implement rate limiting and account lockout policies to prevent brute force attacks.
- Ensure in more robust ways users can only access their own data by implementing ownership checks (preventing `IDOR` vulnerabilities).

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
