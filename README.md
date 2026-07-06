# API Portal Backend Gateway

<p align="center">

A secure, production-ready REST API built with Spring Boot that provides JWT-based authentication, role-based authorization, and tier-based access control for Free and Premium users.

</p>
<p align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge)
![Spring Security](https://img.shields.io/badge/Spring_Security-Enabled-success?style=for-the-badge)
![JWT](https://img.shields.io/badge/JWT-Authentication-blue?style=for-the-badge)
![JPA](https://img.shields.io/badge/JPA-Hibernate-yellow?style=for-the-badge)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge)
![REST API](https://img.shields.io/badge/API-REST-success?style=for-the-badge)

</p>

## Live API

The backend is deployed and publicly accessible, allowing authenticated users to interact with the REST API without requiring a local setup.

### Production URL

https://api-portal-production-123.up.railway.app/

### Swagger UI

https://api-portal-production-123.up.railway.app/swagger-ui/index.html/

## About the Project

API Portal Backend is a secure RESTful backend application developed using **Spring Boot** to demonstrate modern authentication and authorization practices in enterprise Java applications.

The system enables users to create an account, authenticate securely, and access protected API resources using **JWT (JSON Web Tokens)**. Every authenticated request is validated through Spring Security, ensuring that only verified users can access secured endpoints.

To demonstrate real-world access control, the application implements a **tier-based authorization model** with **Free** and **Premium** user roles. Premium endpoints are protected using role-based authorization, preventing Free users from accessing restricted resources while allowing Premium users full access to exclusive content.

The project follows a layered architecture with Controllers, Services, and Repositories, uses **Spring Data JPA** for database interaction, and stores passwords securely using **BCrypt encryption**. Designed with scalability and maintainability in mind, it showcases industry-standard backend development practices for building secure REST APIs.

## Project Goals

This project was built to explore and implement secure backend development practices using the Spring ecosystem. The primary objectives include:

- Building a stateless authentication system using JWT.
- Implementing secure user registration and login.
- Enforcing role-based authorization with Spring Security.
- Restricting premium resources to authorized users.
- Following clean architecture principles for maintainable backend development.
- Providing a deployable REST API that can be consumed by frontend applications or API clients.

## Features

- Secure user registration and login.
- JWT-based stateless authentication.
- Role-based authorization using Spring Security.
- Tier-based access control for Free and Premium users.
- Automatic restriction of premium-only endpoints.
- BCrypt password hashing before database storage.
- RESTful API design following industry standards.
- Database persistence using Spring Data JPA and Hibernate.
- Centralized exception handling for consistent error responses.
- Layered architecture (Controller → Service → Repository).
- Deployed backend accessible via a public URL.

## Project Highlights

| Category | Implementation |
|----------|----------------|
| **Authentication** | Secure user registration and login using JWT (JSON Web Tokens) |
| **Authorization** | Role-Based Access Control (RBAC) with Spring Security |
| **User Tiers** | Supports **Free** and **Premium** user roles |
| **Access Control** | Premium endpoints are accessible only to authenticated Premium users |
| **Password Security** | Passwords are encrypted using BCrypt before being stored |
| **Session Management** | Stateless authentication with JWT, eliminating server-side sessions |
| **API Design** | RESTful architecture with well-structured endpoints and HTTP status codes |
| **Data Persistence** | Spring Data JPA with Hibernate ORM for database operations |
| **Architecture** | Layered architecture (Controller → Service → Repository) for maintainability |
| **Exception Handling** | Centralized global exception handling with consistent API responses |
| **Deployment** | Publicly deployed backend allowing authenticated API access over the internet |
| **Build Tool** | Maven-based dependency and project management |

## Tech Stack

### Backend

| Technology | Purpose |
|------------|---------|
| **Java 21** | Core programming language |
| **Spring Boot** | REST API development and application framework |
| **Spring Security** | Authentication and authorization |
| **JWT (JSON Web Tokens)** | Stateless user authentication |
| **Spring Data JPA** | Database abstraction and persistence |
| **Hibernate** | ORM framework for database interaction |

### Database

| Technology | Purpose |
|------------|---------|
| **PostgreSQL** | Relational database for storing users and application data |

### Build & Dependency Management

| Technology | Purpose |
|------------|---------|
| **Maven** | Dependency management and project build automation |

### Development Tools

| Tool | Purpose |
|------|---------|
| **IntelliJ IDEA** | Primary development environment |
| **Postman** | API testing and endpoint validation |
| **Git** | Version control |
| **GitHub** | Source code hosting and collaboration |

### Deployment

| Platform | Purpose |
|----------|---------|
| **Railway** | Cloud deployment of the backend API |

## System Architecture

The application follows a **layered architecture**, separating responsibilities across different components to improve maintainability, scalability, and testability.

```text
                    Client (Postman / Frontend)
                              │
                              ▼
                    Spring Security Filter
                              │
                     JWT Token Validation
                              │
                              ▼
                         REST Controllers
                              │
                              ▼
                        Service Layer
                              │
                              ▼
                     Repository Layer (JPA)
                              │
                              ▼
                         PostgreSQL Database
```

Each incoming request passes through Spring Security, where the JWT token is validated before access is granted to protected endpoints. Once authenticated and authorized, the request is processed through the Controller, Service, and Repository layers before interacting with the database.

### Architecture Layers

| Layer | Responsibility |
|--------|----------------|
| **Security Filter** | Intercepts incoming requests and validates JWT tokens before allowing access. |
| **Controller** | Handles HTTP requests and returns appropriate API responses. |
| **Service** | Contains business logic and coordinates application operations. |
| **Repository** | Performs database operations using Spring Data JPA. |
| **Database** | Stores user accounts, roles, and application data securely. |

## Request Lifecycle

1. The client sends an HTTP request to the API.
2. Spring Security intercepts the request.
3. The JWT token is extracted from the `Authorization` header.
4. The token is validated for authenticity and expiration.
5. The authenticated user's role is verified.
6. If authorized, the request proceeds to the appropriate controller.
7. The controller delegates processing to the service layer.
8. The service interacts with the repository layer.
9. The repository retrieves or persists data in PostgreSQL.
10. A structured JSON response is returned to the client.

## API Endpoints

The API is organized into logical endpoint groups for authentication and resource access. Interactive documentation is available through Swagger UI, where you can explore every endpoint, view request/response schemas, and execute API calls directly from your browser.

### Endpoint Categories

| Category | Description | Access |
|----------|-------------|--------|
| **Authentication** | Register new users and authenticate existing users | Public |
| **Free APIs** | Endpoints accessible to authenticated Free and Premium users | Authenticated |
| **Premium APIs** | Endpoints restricted to Premium users only | Authenticated & Premium |

> **Interactive API Documentation**
>
> Explore all available endpoints, request bodies, response schemas, and test the API directly using the Swagger UI.
>
> <p align="left"><a href="https://your-api-url.onrender.com/swagger-ui/index.html"><img src="https://img.shields.io/badge/Open%20Swagger%20UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger UI"></a></p>

## Project Structure

```text
api-portal-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/armaan/apiportal/
│   │   │       ├── auth/
│   │   │       ├── config/
│   │   │       ├── model/
│   │   │       ├── portal/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── ApiPortalApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ...
│   └── test/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── README.md
└── LICENSE
```

### Directory Overview

| Directory | Purpose |
|-----------|---------|
| `config` | Application and security configuration classes |
| `portal` | REST API endpoints that handle client requests on dashboard |
| `auth` | REST API endpoints that handle User authentication |
| `model` | JPA entity classes mapped to database tables |
| `repository` | Spring Data JPA repositories for database operations |
| `security` | JWT utilities, authentication filters, and security configuration 
| `resources` | Configuration files and application properties |

## Getting Started

Follow the steps below to set up and run the project locally.

### Prerequisites

Make sure the following software is installed on your machine:

| Requirement | Version |
|-------------|---------|
| Java | 21 or later |
| Maven | 3.9+ |
| PostgreSQL | 15+ (or your version) |
| Git | Latest |

### Clone the Repository

```bash
git clone https://github.com/armaanmulani/api-portal-backend.git

cd api-portal-backend
```

### Configure the Database

Update your `application.properties` (or `application.yml`) with your PostgreSQL credentials.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/api_portal
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

> **Note:** Never commit real credentials or JWT secrets to the repository. Use environment variables or external configuration for production deployments.

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

Once the application starts successfully, it will be available at:

```
http://localhost:8080
```

You can then explore and test the API using the Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

### Environment Variables

| Variable | Description |
|----------|-------------|
| `DB_URL` | PostgreSQL connection URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key used to sign JWT tokens |
| `JWT_EXPIRATION` | JWT expiration time in milliseconds |

## Screenshots

### Swagger UI

Interactive API documentation with all available endpoints.

<p align="center">
  <img src="https://github.com/user-attachments/assets/9ec3494b-9384-4aac-9a6b-fdc2025817dc" width="900" alt="Swagger UI">
</p>

---

### User Registration

Register a new user account.

<p align="center">
  <img src="https://github.com/user-attachments/assets/82d353a3-75eb-489a-a6b4-63ce26ef1e72" width="900" alt="Register Endpoint">
</p>

---

### User Login

Authenticate a user and receive a JWT access token.

<p align="center">
  <img src="https://github.com/user-attachments/assets/c1c96059-0579-42a3-9ac7-c6e273c3f473" width="900" alt="Login Endpoint">
</p>

---

### JWT Authorization

Authorize requests by providing the JWT access token.

<p align="center">
  <img src="https://github.com/user-attachments/assets/f4dced11-2f62-4762-9ff8-f3a1ba5ff1c9" width="900" alt="JWT Authorization">
</p>

---

### Premium Endpoint Access

Premium users can successfully access protected resources.

<p align="center">
  <img src="https://github.com/user-attachments/assets/ecc7abb1-88bd-4096-a7af-facba6b297d0" width="900" alt="Premium Access">
</p>

---

### Access Denied

A Free user attempting to access a Premium endpoint receives a `403 Forbidden` response.

<p align="center">
  <img src="https://github.com/user-attachments/assets/0778ff54-f3a4-49fa-baa3-1704ec54cf6c" width="900" alt="403 Forbidden">
</p>

## Roadmap

The following enhancements are planned for future releases:

- [ ] Refresh token support for improved session management
- [ ] Email verification during user registration
- [ ] Password reset via email
- [ ] OAuth 2.0 authentication (Google/GitHub)
- [ ] API rate limiting to prevent abuse
- [ ] Docker containerization
- [ ] CI/CD pipeline using GitHub Actions
- [ ] Unit and integration testing
- [ ] API versioning
- [ ] Monitoring and logging with Prometheus & Grafana

## Contributing

Contributions, bug reports, and feature suggestions are welcome.

If you'd like to contribute:

1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes with clear commit messages.
4. Open a Pull Request describing your changes.

Please ensure that your code follows the existing project structure and coding conventions.

## License

This project is licensed under the **MIT License**.

See the [LICENSE](LICENSE) file for more information.

## Author

**Armaan Mulani**

- GitHub: https://github.com/armaanmulani
- LinkedIn: https://linkedin.com/in/armaanmulani

If you found this project helpful, consider giving it a ⭐ on GitHub!
