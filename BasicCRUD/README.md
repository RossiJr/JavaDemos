# BasicCrud Demo

This project is a demo for implementing a basic CRUD (Create, Read, Update, Delete) application in a Java + Spring Boot
application.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Docker Setup](#docker-setup)
- [Endpoints](#endpoints)
- [Example Requests](#example-requests)
- [Database Configuration](#database-configuration)
- [License](#license)

## Features

- Basic CRUD operations for managing tasks.
- RESTful APIs for seamless integration.
- Integration with PostgreSQL database.

## Technologies Used

- **Backend**: Java, Spring Boot, Spring Data JPA, PostgreSQL
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
cd JavaDemos/BasicCRUD
```

### Backend Setup

1. Build and run the Spring Boot backend:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Docker Setup

1. Ensure Docker and Docker Compose are installed on your system.

2. Build and start the application using Docker Compose (ensure the application is already built):

   ```bash
   docker-compose up --build
   ```

3. The application should now be running, with the backend accessible at `http://localhost:8080`.

4. To stop the containers:

   ```bash
   docker-compose down
   ```

## Endpoints

### CRUD Operations

- **GET /api/v1/tasks**: Retrieve all tasks.
- **GET /api/v1/tasks/{id}**: Retrieve a specific task by its ID.
- **POST /api/v1/tasks**: Create a new task.
- **PUT /api/v1/tasks/{id}**: Update an existing task.
- **DELETE /api/v1/tasks/{id}**: Delete a task by its ID.

## Example Requests

### Create Task

#### Request

- **Endpoint**: `POST /api/v1/tasks`
- **Body**:
  ```json
  {
    "title": "First task 1",
    "description": "Just a sample",
    "dueDate": "2025-01-09T20:59:00Z",
    "projectId": 1,
    "priority": "HIGH"
    }
  ```

#### Response

- HTTP Status: 201 CREATED
  ```json
  {
    "id": 1,
    "title": "First task 3",
    "description": "Just a sample",
    "createdAt": "2025-01-09T20:15:13.338602085Z",
    "updatedAt": "2025-01-09T20:15:13.338637535Z",
    "dueDate": "2025-01-09T20:59:00Z",
    "priority": "HIGH",
    "status": "TO_DO"
    }
  ```

### Retrieve All Tasks

#### Request

- **Endpoint**: `GET /api/v1/tasks`

#### Response

- HTTP Status: 200 OK
  ```json
  [
    {
    "id": 1,
    "title": "First task 3",
    "description": "Just a sample",
    "createdAt": "2025-01-09T20:15:13.338602085Z",
    "updatedAt": "2025-01-09T20:15:13.338637535Z",
    "dueDate": "2025-01-09T20:59:00Z",
    "priority": "HIGH",
    "status": "TO_DO"
        }
    ]
  ```

### Retrieve Specific Task

#### Request

- **Endpoint**: `GET /api/v1/tasks/1`

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "id": 1,
    "title": "First task 3",
    "description": "Just a sample",
    "createdAt": "2025-01-09T20:15:13.338602085Z",
    "updatedAt": "2025-01-09T20:15:13.338637535Z",
    "dueDate": "2025-01-09T20:59:00Z",
    "priority": "HIGH",
    "status": "TO_DO"
    }
  ```

### Update Task

#### Request

- **Endpoint**: `PUT /api/v1/tasks/1`
- **Body**:
  ```json
  {
    "id": 1,
    "title": "First task 1",
    "description": "Just a sample 1",
    "dueDate": "2025-01-09T20:59:00Z",
    "projectId": 1,
    "priority": "LOW"
    }
  ```

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "id": 1,
    "title": "First task 3",
    "description": "Just a sample",
    "createdAt": "2025-01-09T20:15:13.338602085Z",
    "updatedAt": "2025-01-09T20:15:13.338637535Z",
    "dueDate": "2025-01-09T20:59:00Z",
    "priority": "HIGH",
    "status": "TO_DO"
    }
  ```

### Delete Task

#### Request

- **Endpoint**: `DELETE /api/v1/tasks/1`

#### Response

- HTTP Status: 204 NO CONTENT

## Database Configuration

- The application uses an H2 in-memory database by default for development.
- To access the H2 console, go to: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - **JDBC URL**: `jdbc:h2:mem:testdb`
    - **Username**: `sa`
    - **Password**: (leave blank)

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
