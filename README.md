# Java Demos

This repository contains a collection of Java projects showcasing various features, frameworks, and concepts in Java development. Each project is self-contained and serves as a practical demonstration of specific use cases.

## Table of Contents

- [Projects](#projects)
  1. [PBAC-Authentication Demo](https://github.com/RossiJr/JavaDemos/tree/main/PBAC-Authentication)
  2. [RBAC-Authentication Demo](https://github.com/RossiJr/JavaDemos/tree/main/RBAC-Authentication)
  3. [Basic CRUD Demo](https://github.com/RossiJr/JavaDemos/tree/main/BasicCRUD)
  4. [Basic AWS S3 CRUD Demo](https://github.com/RossiJr/JavaDemos/tree/main/AWSS3-CRUD)
  5. [Project Structure](https://github.com/RossiJr/JavaDemos/tree/main/ProjectStructure)
  6. [Elasticsearch CRUD Demo](https://github.com/RossiJr/JavaDemos/tree/main/ElasticSearch-CRUD)
  7. [Kafka ChatService Demo](https://github.com/RossiJr/JavaDemos/tree/main/Kafka-ChatService)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)

## Projects

### 1. [PBAC-Authentication Demo](https://github.com/RossiJr/JavaDemos/tree/main/PBAC-Authentication)
- **Description**: Demonstrates a secure Permission-Based Access Control (PBAC) authentication system using Java and Spring Boot.
- **Features**:
  - JWT-based authentication
  - Permission-based access control with roles to *group permissions*.
  - RESTful API for user management

### 2. [RBAC-Authentication Demo](https://github.com/RossiJr/JavaDemos/tree/main/RBAC-Authentication)
- **Description**: Demonstrates a secure Role-Based Access Control (RBAC) authentication system using Java and Spring Boot.
- **Features**:
  - JWT-based authentication
  - Role-based access control using roles to access endpoints.
  - RESTful API for user management

### 3. [Basic CRUD Demo](https://github.com/RossiJr/JavaDemos/tree/main/BasicCRUD)
- **Description**: Demonstrates the basics of creating a basic CRUD using Spring Boot e PostgreSQL
- **Features**:
  - Basic CRUD operations for managing tasks.
  - RESTful APIs for seamless integration.
  - Integration with PostgreSQL database.

### 4. [Basic AWS S3 CRUD Demo](https://github.com/RossiJr/JavaDemos/tree/main/AWSS3-CRUD)
- **Description**: Demonstrates a basic CRUD creation using S3 buckets.
- **Features**:
  - Basic CRUD operations for managing files on AWS S3.
  - Supports uploading, reading, updating, and deleting files.
  - Downloads the file when requesting with verb GET.

### 5. [Project Structure](https://github.com/RossiJr/JavaDemos/tree/main/ProjectStructure)
- **Description**: Foundational template for Java applications, providing a structured starting point for development.
- **Features**:
  - Pre-configured project structure following best practices.
  - Sample configurations for common tools and frameworks.
  - Placeholder classes and interfaces to guide development.
  - Role based authentication logic already implemented.

### 6. [Elasticsearch CRUD Demo](https://github.com/RossiJr/JavaDemos/tree/main/ElasticSearch-CRUD)
- **Description**: Demonstrates a CRUD application using **Elasticsearch** with **Spring Boot**.
- **Features**:
  - Basic CRUD operations for managing users in Elasticsearch.
  - Dynamic aggregation queries with support for:
    - Counting users by fields (e.g., `city`, or `age`).
    - Retrieving top cities by age range using dynamic range queries.
    - Full-text search with typo tolerance (fuzziness).
  - Elasticsearch integration using **Spring Data Elasticsearch**.

### 7. [Kafka ChatService Demo](https://github.com/RossiJr/JavaDemos/tree/main/Kafka-ChatService)
- **Description**: This project is a demo for implementing a simple ChatService application with **Kafka** + **SpringBoot** + **MongoDB**. The main goal is to demonstrate the usage of **Kafka** and **WebSockets**.
- **Features**:
  - Send and receive messages in real-time using WebSockets.
  - Save chat history in MongoDB.
  - Use Kafka to broadcast messages to all subscribers.

## Technologies Used

- **Java**: The primary programming language for all projects.
- **Spring Boot**: Framework used for building Java-based backend applications.
- **PostgreSQL**: Database used for some projects.
- **MongoDB**: NoSQL database used for some projects such as Kafka ChatService.
- **Elasticsearch**: Search engine used for the Elasticsearch CRUD demo.
- **Maven**: Build automation tool.
- **Docker**: For containerized application setup (used in specific projects).
- **JUnit**: For testing Java applications.
- **AWS S3**: For file storage and management in the AWS S3 CRUD demo.

## Setup Instructions

1. Clone the repository:

   ```bash
   git clone https://github.com/RossiJr/JavaDemos.git
   cd JavaDemos
   ```

2. Navigate to the specific project directory you want to run.

3. Follow the setup instructions provided in the README file of the selected project.

4. Build and run the project using Maven:

   ```bash
   mvn clean install
   mvn spring-boot:run

> It is nice to say that some inline-comments are automatically generated to improve writing speed. However, **all** of them are read and further improved by the author. 

> It is noteworthy that you can run most of the demos just by using `docker-compose up --build`, instead of configuring them in your own machine.
