# UserApp: Spring Boot CRUD API

A simple **User Management REST API** built with **Spring Boot** and **MySQL**. It supports creating, reading, updating and deleting users, and is organized into clear layers (controller, service, repository, model, dto, exception, scheduler).

## Tech Stack

- Java (17 or newer)
- Spring Boot 4.1.1 (Spring Web, Spring Data JPA, Validation)
- MySQL 8
- Maven

## Architecture

```
Client (Postman) -> Controller -> Service -> Repository -> MySQL
                        |            |
                       DTO       Exception
```

| Layer | Responsibility |
|---|---|
| `model` | JPA entity mapped to the `users` table |
| `repository` | Database access (`JpaRepository`, derived queries, `@Query`) |
| `service` | Business logic (interface + implementation) |
| `controller` | REST routes and HTTP methods |
| `dto` | Request and response data shapes, with validation |
| `exception` | Custom exceptions and a global exception handler |
| `scheduler` | Background task that runs on a fixed schedule |

## Project Structure

```
userapp
└── src/main
    ├── java/com/example/userapp
    │   ├── UserappApplication.java
    │   ├── controller
    │   │   └── UserController.java
    │   ├── service
    │   │   ├── UserService.java
    │   │   └── UserServiceImpl.java
    │   ├── repository
    │   │   └── UserRepository.java
    │   ├── model
    │   │   └── User.java
    │   ├── dto
    │   │   ├── UserRequestDto.java
    │   │   └── UserResponseDto.java
    │   ├── exception
    │   │   ├── ResourceNotFoundException.java
    │   │   └── GlobalExceptionHandler.java
    │   └── scheduler
    │       └── UserScheduler.java
    └── resources
        ├── application.properties
        └── application-local.properties   (not committed)
```

## Getting Started

### 1. Prerequisites

- JDK 17 or newer
- MySQL 8 running locally
- Maven (or use the included `mvnw` wrapper)

### 2. Clone the repository

```bash
git clone https://github.com/dasunisenanayake25/userapp.git
cd userapp
```

### 3. Create the database

```sql
CREATE DATABASE userdb;
```

The `users` table is created automatically by Hibernate on first run (`spring.jpa.hibernate.ddl-auto=update`).

### 4. Configure the database password

The database password is **not** stored in the repository. Create this file yourself:

`src/main/resources/application-local.properties`

```properties
spring.datasource.password=your_mysql_password
```

This file is listed in `.gitignore`, so it will never be committed.

### 5. Run the application

Run with the `local` profile so the password file is picked up.

**Command line:**

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

On Windows PowerShell use `.\mvnw spring-boot:run "-Dspring-boot.run.profiles=local"`.

**IntelliJ IDEA:** Run > Edit Configurations > `UserappApplication` > Active profiles: `local`.

The API starts on `http://localhost:8080`.

## Configuration

`src/main/resources/application.properties`

```properties
spring.application.name=userapp
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## API Endpoints

Base URL: `http://localhost:8080/api/users`

| Method | Endpoint | Description | Success |
|---|---|---|---|
| POST | `/api/users` | Create a user | 201 Created |
| GET | `/api/users` | Get all users | 200 OK |
| GET | `/api/users/{id}` | Get a user by id | 200 OK |
| PUT | `/api/users/{id}` | Update a user | 200 OK |
| DELETE | `/api/users/{id}` | Delete a user | 204 No Content |

### Request body (POST and PUT)

```json
{
  "fullName": "Nimal Perera",
  "email": "nimal@mail.com"
}
```

### Response body

```json
{
  "id": 1,
  "fullName": "Nimal Perera",
  "email": "nimal@mail.com",
  "status": "ACTIVE"
}
```

### Validation rules

- `fullName` must not be blank
- `email` must not be blank and must be a valid email address
- `email` must be unique

### Error responses

Errors are returned as JSON:

| Situation | Status | Example body |
|---|---|---|
| User not found | 404 Not Found | `{"error": "User not found with id: 99"}` |
| Invalid request body | 400 Bad Request | `{"error": "Full name is required"}` |

## Scheduler

`UserScheduler` runs every 60 seconds and prints the total number of users in the database to the console:

```
Total users in DB: 3
```

Scheduling is enabled with `@EnableScheduling` on the main application class.

## Git Workflow

Each layer was developed on its own branch and merged into `main` through a pull request:

`model` -> `repository` -> `dto` -> `exception` -> `service` -> `controller` -> `scheduler`