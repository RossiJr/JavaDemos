# BasicCrud Demo

This project is a demo for implementing a basic CRUD application using AWS S3 with Java and Spring Boot.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Authentication Methods](#authentication-methods)
- [Endpoints](#endpoints)
- [Example Requests](#example-requests)

## Features

- Basic CRUD operations for managing files on AWS S3.
- Supports uploading, reading, updating, and deleting files.
- Downloads the file when requesting with verb GET.

## Technologies Used

- **Backend**: Java, Spring Boot, AWS SDK
- **Tools**: IntelliJ IDEA, Maven, Postman for API testing

## Setup Instructions

### Prerequisites

1. Ensure you have the following installed:
    - Java 17 or later
    - Maven
    - AWS CLI (configured with credentials and profiles) or IAM role with S3 permissions
    - AWS S3 bucket previous created and configured
2. Clone the repository and navigate to the project folder.

```bash
git clone https://github.com/RossiJr/JavaDemos.git
cd JavaDemos\4_AWSS3CRUD
```

### Backend Setup

1. Build and run the Spring Boot backend:

   ```bash
   mvn clean install -DskipTests
   mvn spring-boot:run
   ```
    > **Note**: Ensure your AWS credentials are configured in the environment variables. Also be sure you have the bucket already created and configured.

2. Ensure your `application.properties` file is configured with the following:

   ```properties
   aws.accessKeyId=YOUR_ACCESS_KEY_ID
   aws.secretAccessKey=YOUR_SECRET_ACCESS_KEY
   aws.region=YOUR_REGION
   s3.bucket.name=YOUR_BUCKET_NAME
   ```

## Authentication Methods

This application supports three different authentication methods for connecting to AWS S3 (many more can be implemented). Below is a description of each method and how to set it up correctly.

### 1. Secret Key Hardcoded

This method uses hardcoded credentials (Access Key ID and Secret Access Key) to authenticate.
It is recommended to use environment variables to avoid exposing sensitive information in the code.

#### Implementation
```java
@Value("${aws.accessKeyId}")
private String accessKeyId;
@Value("${aws.secretAccessKey}")
private String secretAccessKey;

private StaticCredentialsProvider secretKeyCredentialsProvider() {
    return StaticCredentialsProvider.create(
            AwsBasicCredentials.create(
                    accessKeyId,
                    secretAccessKey
            )
    );
}
```

#### Setup Steps

1. Open you `application.properties` file;
2. Add the following properties:
```properties
aws.accessKeyId=YOUR_ACCESS_KEY_ID
aws.secretAccessKey=YOUR_SECRET_ACCESS_KEY
```
3. Ensure those credentials are correctly configured to acces the resources. 

### 2. Profile Credentials Provider

This method uses a profile configured in the AWS CLI for authentication.

#### Implementation

```java
@Value("${aws.profileName}")
private String profileName;

private ProfileCredentialsProvider profileCredentialsProvider() {
    return ProfileCredentialsProvider.builder()
            .profileName(profileName)
            .build();
}
```

#### Setup Steps
1. Configure the profile in the AWS CLI:
```bash
aws configure --profile YOUR_PROFILE_NAME
```
> Provide the Access Key ID, Secret Access Key, and default region when prompted.
2. Check if the configuration is correct by listing the S3 buckets:
```bash
aws s3 ls --profile YOUR_PROFILE_NAME
```
3. Ensure to add the profile name to the `application.properties` file and/or to your environment variables.
```properties
aws.profileName=YOUR_PROFILE_NAME
```

### 3. Instance Profile Credentials

This method is ideal when running the application on an AWS service (e.g., EC2 or ECS) with an assigned IAM Role, which makes it easier.

#### Implementation

```java
private InstanceProfileCredentialsProvider instanceRoleCredentialsProvider() {
    return InstanceProfileCredentialsProvider.create();
}
```

#### Setup Steps

1. Assign an IAM Role with the required S3 permissions to the EC2 or ECS instance/task;
2. Ensure the application is running on the EC2 or ECS instance/task;
3. As the SDK automatically retrieves the credentials from the instance metadata, no additional configuration is needed.


## Endpoints

### CRUD Operations

- **POST /s3**: Upload a file to S3.
- **GET /s3/{key}**: Retrieve a specific file by key (downloads the file when calling from the browser)
- **PUT /s3/{key}**: Replace a file with a new one.
- **DELETE /s3/{key}**: Delete a file by its key.

## Example Requests

### Upload File

#### Request

- **Endpoint**: `POST /s3`
- **Body**: Multipart file with key `file` and value the file to upload.

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "message": "File uploaded successfully with key: sample.pdf"
  }
  ```

### Retrieve File (Exhibit PDF Inline)

#### Request

- **Endpoint**: `GET /s3/sample.pdf`

#### Response

- HTTP Status: 200 OK
- Content-Disposition: attachment; filename="sample.pdf"

### Replace File

#### Request

- **Endpoint**: `PUT /s3/sample.pdf`
- **Body**: Multipart file

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "message": "File updated successfully with key: sample.pdf"
  }
  ```

### Delete File

#### Request

- **Endpoint**: `DELETE /s3/sample.pdf`

#### Response

- HTTP Status: 200 OK
  ```json
  {
    "message": "File deleted successfully with key: sample.pdf"
  }
  ```



> Use the correct Build-Lifecycle from Maven (in this project we are skipping many phases and generating the JAR without testing, as it is a demo designed to local development only).