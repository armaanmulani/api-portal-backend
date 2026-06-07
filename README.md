# Enterprise API Portal Backend Gateway

A high-performance, fully containerized security and traffic management backend engine built with **Spring Boot 3** and **Spring Security 6**. This architecture handles a robust stateless gateway pipeline featuring dynamic role-based rate-limiting, secure dual-token session tracking, database-backed token blacklisting for immediate session revocation, and a centralized global exception interceptor pipeline.

---

## 🚀 Core Architectural Features

### 1. Robust Security Filter Chain & Session Tracking
* **Stateless JWT Authentication:** Utilizes an optimized JSON Web Token engine that extracts, validates, and serializes user roles (`FREE` vs `PRO`) directly into short-lived access tokens to protect core downstream dashboard routes.
* **Dual-Token Lifecycle Architecture:** Integrates database-backed, long-lived `UUID` refresh tokens with automated rotation and transactional cleanup to handle seamless, silent session extensions without password prompts.
* **Server-Side Token Blacklisting (True Logout):** Eliminates standard stateless JWT vulnerabilities by computing token Time-To-Live (TTL) on logout actions, caching signatures inside a database block list to reject subsequent hijacked requests instantly.

### 2. High-Performance Traffic Control (Rate-Limiting)
* **Bucket4j Token Bucket Implementation:** Decoupled completely from core servlet configurations using a high-performance **Spring MVC HandlerInterceptor** running immediately after identity finalization.
* **Dynamic Tier Throttling:** Programmatically allocates individual, thread-safe virtual token buckets mapped to active user context identities:
  * **FREE Tier:** Strictly capped at `10 requests / minute` with instant `429 Too Many Requests` mitigation drop-offs.
  * **PRO Tier:** Accelerated to `100 requests / minute` with an absolute auto-refill interval strategy.

### 3. Defensive Programming & Global Interception
* **Centralized Exception Catchment:** Engineered using a unified `@RestControllerAdvice` to trap multi-threaded security faults (`AccessDeniedException`, custom token failures) before internal servlet forward leak routes can occur.
* **Unified API Response Layouts:** Formats all application errors into predictable, structured JSON objects containing exact HTTP statuses, human-readable feedback, and precise request tracking identifiers.

---

## 🛠️ Technology Stack
* **Framework:** Spring Boot 3.x, Spring Security 6.x, Spring Data JPA
* **Database Engine:** MySQL 8.0 
* **Traffic Control Core:** Bucket4j (Token-Bucket Algorithm)
* **Token Handling:** JJWT (Java JWT Library)
* **Build & Dependency Automation:** Maven 3+
* **Containerization Engine:** Docker & Docker Compose

---

## 🐋 Containerized Local Deployment (Docker Architecture)

The entire application ecosystem—including the compiled Java 21 Spring Boot web container and a pre-configured, isolated MySQL instance—can be deployed on any machine with **one single command** via Docker Compose.

### Prerequisites
Ensure you have the following installed on your machine:
* [Docker Desktop](https://www.docker.com/products/docker-desktop/)
* Git

### Deployment Steps
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/armaan941/api-portal-backend.git
   cd api-portal-backend
2. **Verify Application Properties Configuration:**
   Ensure your local src/main/resources/application.properties file is configured to dynamically read environmental variables passed down by the Docker Compose engine:
   ```bash
   spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/api_portal_db}
   spring.datasource.username=${SPRING_DATASOURCE_USERNAME:root}
   spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:your_local_password}
3. **Launch the Containerized Infrastructure:**
   Run the following terminal command from the project root directory (where `docker-compose.yml` resides):
   ```bash
   docker compose up --build -d
   ```
   This command compiles your Spring Boot source code inside a multi-stage Maven builder image, fetches a stable MySQL 8 container instance, provisions an isolated virtual bridge network, maps database volumes,      and launches both services seamlessly in detached background mode.
4. **Verify Container Health Status:**
   ```bash
   docker compose ps
   ```
   You should see two healthy containers actively executing:
   * `api-portal-app` listening on host port `8000`
   * `api-portal-db` routed safely to host port `3307` (configured to completely bypass local machine 3306 socket resource collisions)

5. **Tear Down / Stop Infrastructure:**
   To gracefully shut down the runtime containers while maintaining your database records safely intact inside the virtual storage volume, run:
   ```bash
   docker compose down

---

## 📝 Integrated API Testing Guide (Postman Verification)

### Endpoints Map
* **Registration:** `POST` `http://localhost:8000/api/v1/auth/register`
* **Authentication:** `POST` `http://localhost:8000/api/v1/auth/login`
* **Token Rotation:** `POST` `http://localhost:8000/api/v1/auth/refresh-token`
* **Protected Free Resource:** `GET` `http://localhost:8000/api/v1/dashboard/free-data`
* * **Protected Premium Resource:** `GET` `http://localhost:8000/api/v1/dashboard/premium-data`
* * **Session Revocation:** `POST` `http://localhost:8000/api/v1/auth/logout`

### Execution Verification Flow
1. Fire up your Postman Client.
2. Target the registration route with a raw JSON request body payload to generate a brand-new database profile entry.
3. Submit a `POST` request to the `/login` endpoint. Copy the generated `accessToken` returned from the success payload response.
4. Access the protected dashboard route, navigate to the Authorization Tab, set the type to Bearer Token, and paste your string key. Click Send to see a clean HTTP `200 OK` return.
5. Testing the Rate Limiter Interceptor: Click the send button rapidly 11 times inside 60 seconds. On the 11th click, the Bucket4j rate-limiting handler interceptor will drop the request thread immediately, short-circuiting execution to return a structured JSON HTTP `429 Too Many Requests` error mapping payload block!

---

### **Commit and Push to GitHub**
To instantly push this completely comprehensive readme update to your profile, execute these final terminal steps:

```bash
git add README.md
git commit -m "Docs: Complete overhaul of README layout adding architecture breakdowns, run guides, and troubleshooting data"
git push origin main
