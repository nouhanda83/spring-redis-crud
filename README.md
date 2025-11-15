Spring Boot + Redis CRUD Project
Overview

This project is a full-featured CRUD application built using Spring Boot and Redis. It demonstrates:

How to connect a Spring Boot application with Redis.

Performing CRUD (Create, Read, Update, Delete) operations with Redis.

Containerization using Docker for easy deployment.

Modern, production-ready project structure with REST APIs.

Redis is used as an in-memory data store, providing fast access to user data, which is ideal for applications that require high performance.

Features

RESTful APIs for managing users:

Create a new user

Get a single user by ID

Get all users

Delete a user

Redis Hash Storage:

Users are stored in Redis using a hash (USERS) for structured and fast access.

Containerized:

Both Redis and Spring Boot run in Docker containers using docker-compose.

UUID-based User IDs for unique identification.

Lightweight and fast, thanks to Redis in-memory storage.

Easily extensible for additional models and services.

Project Structure
com.redis.example/
│
├── src/main/java/com/api/redis/
│   ├── config/
│   │   └── RedisConfig.java      # Redis connection configuration
│   ├── dao/
│   │   └── UserDao.java          # DAO layer for Redis operations
│   ├── models/
│   │   └── User.java             # User model class
│   └── controllers/
│       └── UserController.java   # REST endpoints for user CRUD
│
├── Dockerfile                    # Spring Boot Docker image configuration
├── docker-compose.yml             # Runs Redis + Spring Boot containers
├── pom.xml                        # Maven project configuration
└── README.md                      # Project documentation

Prerequisites

Java 17+

Maven

Docker & Docker Compose (optional if running locally without containers)

Redis (can run locally or in Docker)

Running Locally

Start Redis (if not using Docker):

redis-server


Build and run Spring Boot application:

mvn clean package -DskipTests
java -jar target/com.redis.example-0.0.1-SNAPSHOT.jar


Test API Endpoints (using Postman or curl):

Create User:
POST http://localhost:8080/users

{
  "name": "John Doe",
  "phone": "1234567890",
  "email": "john@example.com"
}


Get Single User:
GET http://localhost:8080/users/{userId}

Get All Users:
GET http://localhost:8080/users

Delete User:
DELETE http://localhost:8080/users/{userId}

Running with Docker

This project comes with Docker support. Both Redis and the Spring Boot app can run inside containers.

Build and start containers:

docker compose up --build


Access the application:

Spring Boot API: http://localhost:8080

Redis: localhost:6379

Stop containers:

docker compose down

Redis Configuration

Redis connection is defined in RedisConfig.java.

Spring Boot uses RedisTemplate<String, Object> to interact with Redis.

Users are stored under the key: USERS in a Redis hash.

Default Redis host and port (for Docker):

Host: redis
Port: 6379

Docker Details

Dockerfile:

Uses OpenJDK 17 slim as base image.

Builds Spring Boot application JAR and copies into the container.

Exposes port 8080.

docker-compose.yml:

Defines two services:

Redis (redis:latest) on port 6379

Spring Boot app on port 8080, depends on Redis

Environment variables for Redis host/port are injected into Spring Boot.

Notes--------

UUIDs are automatically generated for each user.

This project is extensible: you can add more models, services, and Redis data structures like Lists, Sets, and Sorted Sets.

Using Redis improves performance for applications requiring fast, in-memory storage.

Author:-
Satyam Mishra
GitHub: https://github.com/saksham869
