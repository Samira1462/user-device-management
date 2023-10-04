# user-device-management
# Backend Coding Challenge

## Introduction
This document serves as a guide for the Backend Coding Challenge project, where the task is to create a small SpringBoot application with Kotlin that provides various endpoints for managing users'device. The application should use an H2 database for simplicity.

## Endpoints
The project requires the implementation of the following endpoints:

1. **Create a Device**
    - Endpoint: `POST /devices`
    - Request Body:
        - Serial Number (String)
        - UUID
        - Phone Number
        - Model (String)
    - Description: This endpoint allows the creation of a new device by providing its details.

2. **Create a User**
    - Endpoint: `POST /users`
    - Request Body:
        - First Name
        - Last Name
        - Address
        - Birthday
    - Description: This endpoint enables the creation of a new user with their personal information.

3. **Assign a Device to a User**
    - Endpoint: `POST /paged/devices`
    - Path Parameters:
        - userId: The ID of the user to whom the device should be assigned.
        - deviceDto: The deviceDto to user assigned.
    - Description: This endpoint assigns a device to a specific user.

4. **List All Users with Devices**
    - Endpoint: `GET /users/paged/devices`
    - Description: This endpoint retrieves a list of all users along with the devices assigned to them.
   
5.**List All Users with Devices**
   - Endpoint: `GET /users`
   - Description: This endpoint retrieves a list of all users.

## Technology Stack
The project uses the following technologies:

- **SpringBoot**: A popular Java framework for building web applications and RESTful services.
- **Kotlin**: A statically-typed programming language that runs on the Java Virtual Machine (JVM) and is fully interoperable with Java.
- **H2 Database**: An in-memory relational database, suitable for development and testing purposes.

## Getting Started
To get started with the Backend Coding Challenge, follow these steps:

1. **Clone the Repository**
   https://github.com/Samira1462/user-device-management

2.**Access the Application**
   The application will be available at `http://localhost:8081`. You can use a tool like Postman or curl to interact with the provided endpoints.
or user swagger http://localhost:8081/swagger-ui/index.html

## Database
The application uses the H2 in-memory database for data storage. The database can be accessed at `http://localhost:8081/h2-console` while the application is running. Use the following connection settings:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave it blank)

## Project Structure
The project follows a standard Spring Boot application structure with the following key directories:

- `src/main/kotlin/com/example/backendcodingchallenge`: Contains the main application code.
- `src/test/kotlin/com/example/backendcodingchallenge`: Contains test cases.

