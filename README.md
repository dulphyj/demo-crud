# Demo CRUD API with Spring Boot and MongoDB

## Overview

This is a simple demo CRUD API service built with **Spring Boot** and **MongoDB**. The application demonstrates basic operations such as **Create**, **Read**, **Update**, and **Delete** using MongoDB as the data store and JWT for authentication. It also includes **Swagger UI** for easy API documentation and testing.

## Prerequisites

- **Java 17** or later
- **Maven** (for building the project)
- **MongoDB Atlas** account or local MongoDB instance
- **JWT Secret Key** (for secure authentication)

## Installation

### Clone the Repository

Clone this repository to your local machine:

```bash
git clone https://github.com/yourusername/demo-crud.git
cd demo-crud
```
Build and Run
To build the project, use Maven:

```bash
mvn clean install
```
To run the application:
```bash
mvn spring-boot:run
```
The application will start on http://localhost:8080 by default.
Configuration
application.properties
Make sure to configure the application.properties file as shown below:
```bash
# Application settings
spring.application.name=demo-crud
server.port=8080

# MongoDB connection
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster-name>.mongodb.net/<database-name>?retryWrites=true&w=majority&appName=testClauster

# JWT Secret Key (replace with your own secret)
jwt.secret=yourSecretKeyHere

# Springdoc OpenAPI configuration for Swagger UI
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/api/doc/swagger-ui.html
springdoc.api-docs.path=/api/doc/api-docs
springdoc.paths-to-match=/api/**
```
Replace the MongoDB connection URI with your own, using the correct credentials and cluster information. Also, replace the jwt.secret value with a secret key of your choice to ensure the JWT token's security.

Example MongoDB URI
```bash
spring.data.mongodb.uri=mongodb+srv://yourusername:yourpassword@yourcluster.mongodb.net/yourdatabase?retryWrites=true&w=majority
```
JWT Secret Key
```bash
jwt.secret=yourSecretKeyHere
```
Replace yourSecretKeyHere with a secure, randomly generated string.

Dependencies
This project uses the following dependencies:

Spring Boot (Web, Data MongoDB, Security, Validation)
Lombok (for reducing boilerplate code)
JJWT (for JWT token generation and validation)
Springdoc OpenAPI (for automatic generation of API documentation)
API Documentation
Once the application is running, you can access the Swagger UI at the following URL:
```bash
http://localhost:8080/api/doc/swagger-ui.html
```
You can view the API documentation, try out the different endpoints, and make requests directly from the browser.

Endpoints
Authentication
POST /api/auth/login - Authenticate and receive a JWT token.
CRUD Operations for Items
GET /api/items - Get all items.
GET /api/items/{id} - Get an item by ID.
POST /api/items - Create a new item.
PUT /api/items/{id} - Update an existing item.
DELETE /api/items/{id} - Delete an item by ID.
Testing
Unit Tests
This project includes unit tests for the service and repository layers. To run the tests, use the following Maven command:
```bash
mvn test

```
JWT Authentication
The API requires JWT authentication for most of the endpoints. You can obtain a JWT token by sending a POST request to the /api/auth/login endpoint with your credentials. The token should then be included in the Authorization header for subsequent requests.

Example:
```bash
curl -X GET "http://localhost:8080/api/items" -H "Authorization: Bearer your_jwt_token_here"
```
License
This project is licensed under the MIT License - see the LICENSE file for details.

Developer Notes
This project is intended for learning and demonstration purposes. For production systems, consider implementing additional security measures, such as encryption for sensitive data and more complex authentication strategies.