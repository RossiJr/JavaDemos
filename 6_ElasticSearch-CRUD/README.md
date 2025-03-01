# Elasticsearch CRUD Demo

This project demonstrates a CRUD (Create, Read, Update, Delete) application using **Elasticsearch** with **Spring Boot
**. The application showcases how to perform basic operations and more advanced aggregation queries with Elasticsearch.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
  - [Local Setup (not Recommended)](#local-setup-not-recommended)
  - [Docker Setup (Recommended)](#docker-setup-recommended)
    - [Manual approach](#manual-approach)
    - [Docker Compose approach](#docker-compose-approach)
- [Endpoints](#endpoints)
- [Example Requests](#example-requests)
- [Notes](#notes)

## Features

- Basic CRUD operations for managing users in Elasticsearch.
- Dynamic aggregation queries with support for:
    - Counting users by fields (e.g., `city`, or `age`).
    - Retrieving top cities by age range using dynamic range queries.
    - Full-text search with typo tolerance (fuzziness).
- Elasticsearch integration using **Spring Data Elasticsearch**.

## Technologies Used

- **Backend**: Java, Spring Boot, Elasticsearch
- **Tools**: IntelliJ IDEA, Maven, Postman for API testing
- **Database**: Elasticsearch

## Setup Instructions

### Prerequisites

1. Ensure you have the following installed:
    - **Java 17** or later
    - **Maven**
2. Clone the repository and navigate to the project folder.

```bash
git clone https://github.com/RossiJr/JavaDemos.git
cd JavaDemos/6_Elasticsearch-CRUD
```

### Local Setup (not Recommended)

> Linux based commands are used in the following steps. For Windows, use the equivalent commands.

1. Ensure you have the following installed:
    - Elasticsearch (tested with version 8.11)
2. If not, download and start Elasticsearch:

    ```bash
    wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.11.0.tar.gz
    tar -xzf elasticsearch-8.11.0.tar.gz
    cd elasticsearch-8.x.x
    ./bin/elasticsearch
    ```

3. Build and run the Spring Boot application:

    ```bash
    mvn clean install -DskipTests
    mvn spring-boot:run
    ```

4. Ensure your application.properties file is correctly configured:

    ```properties
    # Or "https://localhost:9200" for HTTPS, depends on your Elasticsearch configuration
    spring.elasticsearch.uris=http://localhost:9200
    spring.elasticsearch.username=YOUR_USERNAME
    spring.elasticsearch.password=YOUR_PASSWORD
    ```

### Docker Setup (Recommended)

1. Ensure Docker and Docker Compose are installed in your system.

#### Manual approach

2. Start Elasticsearch with Docker on port 9200 manually:

    ```bash
    docker run -d --name elasticsearch \
      -e "discovery.type=single-node" \
      -e "xpack.security.enabled=false" \
      -p 9200:9200 -p 9300:9300 \
      docker.elastic.co/elasticsearch/elasticsearch:8.11.0
    ```

3. Verify Elasticsearch is running (if you see a JSON response with no errors, it's working ;):

    ```bash
    curl http://localhost:9200
    ```

4. Ensure that your application.properties file is correctly configured:

    ```properties
    spring.elasticsearch.uris=http://localhost:9200
    ```

5. Build and run the Spring Boot application:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

#### Docker Compose approach

2. Ensure your application.properties file is correctly configured:

    ```properties
    spring.elasticsearch.uris=http://elasticsearch:9200
    ```

3. Start Elasticsearch with Docker Compose:

    ```bash
    docker-compose up -d
    ```

4. Verify if Elasticsearch is running (if you see a JSON response with no errors, it's working ;):

    ```bash
    curl http://localhost:9200
    ```

5. If everything is running fine, your Spring Boot container will also be available at `http://localhost:8080`. So you
   can perform the operations in the `/users` endpoint.

## Endpoints

### CRUD Operations

- **POST `/users`**: Create a new user.
- **GET `/users/{id}`**: Retrieve a user by ID.
- **GET `/users`**: Retrieve all users.
- **DELETE `/users/{id}`**: Delete a user by ID.

> **Note**: the PUT (update) operation is not implemented, but it can be easily implemented by following the same
> pattern as the other operations.

### Query Based Operations

- **GET `/users/query`**: perform dynamic aggregation queries (only one option should be used at a time).
    - **GET `/users/query?name={name}`**: search users by name
    - **GET `/users/query?createdWithin={x}`**: search users created within the last X days.
    - **GET `/users/query?keyword={keyword}`**: search users across `name` and `city` fields, with fuzzy search -
      meaning that it will return results even if there are typos in the search term.
    - **GET `/users/query?countBy={field}`**: aggregate users by a specific field (e.g., `city`, `age`).
    - **GET `/users/query?topCitiesByAgeRange={from}-{to}`**: aggregate top cities by age range - meaning that it will
      return the top cities where users are between the specified age range.

## Example Requests

> Those don't use the best practices of RESTful APIs, as they are used for demonstration purposes and the objetive is to
> show the Elasticsearch functionalities.
> So, I am sure that you can improve the Controller and Service layer.

### Create a User

#### Request

- Endpoint `POST /users`
- Body:
   ```json
   {
       "name": "John Doe",
       "age": 30,
       "city": "New York"
   }
   ```

#### Response

- HTTP Status: 200 OK
    - Body:
       ```json
       {
         "id": "abc123",
         "name": "John Doe",
         "age": 30,
         "city": "New York",
         "createdAt": "2025-02-08T15:30:00Z"
       }
       ```

### Perform Full-Text Search

#### Request

- **Endpoint**: `GET /users/query?keyword=francisc`

#### Response

- **HTTP Status**: 200 OK
  ```json
      [
          {
            "id": "def456",
            "name": "Jane Developer",
            "age": 28,
            "city": "San Francisco",
            "createdAt": "2025-02-08T15:35:00Z"
          }
      ]

  ```

### Get Top Cities by Age Range

#### Request

- **Endpoint**: `GET /users/query?countBy=city`

#### Response

- **HTTP Status**: 200 OK
  ```json
  [
    {
      "key": "New York",
      "count": 10
    },
    {
      "key": "Los Angeles",
      "count": 7
    }
  ]
  ```
  
## Notes

 - Use the correct Build-Lifecycle from Maven (in this project we are skipping many phases and generating the JAR without testing, as it is a demo designed to local development only).
 - The project uses Spring Data Elasticsearch for integrating with Elasticsearch.
 - Ensure your Elasticsearch instance is running before starting the application.
 - Use Postman or similar tools to test the APIs.