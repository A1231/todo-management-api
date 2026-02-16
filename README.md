# Todos Application

A RESTful Todo Management API built with Spring Boot, featuring JWT-based authentication, role-based access control, and comprehensive user management capabilities.

## Features

- 🔐 **JWT Authentication** - Secure token-based authentication
- 👥 **User Management** - User registration, login, and profile management
- ✅ **Todo Management** - Create, read, update, and delete todos
- 🔑 **Role-Based Access Control** - Admin and user roles with different permissions
- 📚 **API Documentation** - Swagger/OpenAPI documentation available
- 🗄️ **MySQL Database** - Persistent data storage with JPA/Hibernate
- 🛡️ **Spring Security** - Comprehensive security configuration

## Tech Stack

- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database persistence
- **MySQL** - Relational database
- **JWT (JJWT)** - JSON Web Token implementation
- **SpringDoc OpenAPI** - API documentation
- **Maven** - Build and dependency management

## Prerequisites

Before running the application, ensure you have:

- Java 17 or higher
- Maven 3.6+ 
- MySQL 8.0+ (running on port 3307)
- A MySQL database named `tododb`

## Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE tododb;
```

2. Update the database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/tododb?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=your_password
```

## Configuration

The application uses the following configuration (in `application.properties`):

- **Database**: MySQL on port 3307
- **JWT Secret**: Configured in `spring.jwt.secret`
- **JWT Expiration**: 900000ms (15 minutes)
- **Swagger UI**: Available at `/docs`

## Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using Maven Wrapper

**Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080` (default Spring Boot port).

## API Endpoints

### Authentication Endpoints (`/api/auth`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Login and get JWT token | No |

### Todo Endpoints (`/api/todos`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/todos` | Create a new todo | Yes |
| GET | `/api/todos` | Get all todos for the authenticated user | Yes |
| PUT | `/api/todos/{id}` | Toggle todo completion status | Yes |
| DELETE | `/api/todos/{id}` | Delete a todo | Yes |

### User Endpoints (`/api/users`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/users/info` | Get current user information | Yes |
| PUT | `/api/users/password` | Update user password | Yes |
| DELETE | `/api/users` | Delete current user account | Yes |

### Admin Endpoints (`/api/admin`)

| Method | Endpoint | Description | Auth Required | Role Required |
|--------|----------|-------------|---------------|---------------|
| GET | `/api/admin` | Get all users in the system | Yes | ADMIN |
| PUT | `/api/admin/{userId}/role` | Promote user to admin | Yes | ADMIN |
| DELETE | `/api/admin/{userId}` | Delete a non-admin user | Yes | ADMIN |

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:

```
http://localhost:8080/docs
```

This provides an interactive interface to explore and test all API endpoints.

## Authentication

The application uses JWT (JSON Web Token) for authentication. After successful login:

1. You will receive a JWT token in the response
2. Include this token in the `Authorization` header for protected endpoints:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## Project Structure

```
src/main/java/com/todos/todos/
├── config/              # Security and Swagger configuration
├── controller/          # REST API controllers
├── entity/              # JPA entities (User, Todo, Authority)
├── exception/           # Exception handling
├── repository/          # JPA repositories
├── request/             # Request DTOs
├── response/            # Response DTOs
├── service/             # Business logic services
└── util/                # Utility classes
```

## Security

- All endpoints except `/api/auth/**` and Swagger documentation require authentication
- Admin endpoints (`/api/admin/**`) require the `ADMIN` role
- JWT tokens are stateless and expire after 15 minutes
- Passwords are securely hashed using Spring Security's BCrypt

## Building the Application

To build the application:

```bash
mvn clean package
```

This will create a JAR file in the `target` directory that can be run with:

```bash
java -jar target/todos-0.0.1-SNAPSHOT.jar
```

## Testing

Run tests with:

```bash
mvn test
```

## License

This project is a demo application for Spring Boot.

## Author

Demo project for Spring Boot

