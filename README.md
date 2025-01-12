# Java Demos

This repository contains a collection of Java projects showcasing various features, frameworks, and concepts in Java development. Each project is self-contained and serves as a practical demonstration of specific use cases.

## Table of Contents

- [Projects](#projects)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)

## Projects

### 1. [Authentication Demo](https://github.com/RossiJr/JavaDemos/tree/main/Authentication)
- **Description**: Demonstrates a secure authentication system using Java and Spring Boot.
- **Features**:
  - JWT-based authentication
  - Role-based access control
  - RESTful API for user management

### 2. [Basic CRUD Demo](https://github.com/RossiJr/JavaDemos/tree/main/BasicCRUD)
- **Description**: Demonstrates the basics of creating a basic CRUD using Spring Boot e PostgreSQL
- **Features**:
  - Basic CRUD operations for managing tasks.
  - RESTful APIs for seamless integration.
  - Integration with PostgreSQL database.

### 3. [Basic AWS S3 CRUD Demo](https://github.com/RossiJr/JavaDemos/tree/main/AWSS3-CRUD)
- **Description**: Demonstrates a basic CRUD creation using S3 buckets.
- **Features**:
  - Basic CRUD operations for managing files on AWS S3.
  - Supports uploading, reading, updating, and deleting files.
  - Downloads the file when requesting with verb GET.

## Technologies Used

- **Java**: The primary programming language for all projects.
- **Spring Boot**: Framework used for building Java-based backend applications.
- **PostgreSQL**: Database used for some projects.
- **Maven**: Build automation tool.
- **Docker**: For containerized application setup (used in specific projects).
- **JUnit**: For testing Java applications.

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

> It is noteworthy that you can run most of the demos just by using `docker-compose up --build`, instead of configuring them in your own machine.
