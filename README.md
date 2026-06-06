# Enterprise API Portal Backend Gateway

A high-performance, containerized security and traffic management backend engine built with **Spring Boot 3** and **Spring Security 6**. This architecture implements a robust stateless gateway pipeline featuring dynamic role-based rate-limiting, secure dual-token session tracking, database-backed token blacklisting for immediate revocation, and a centralized global exception interceptor.

---

## 🚀 Core Architectural Features

### 1. Robust Security Filter Chain & Session Tracking
* **Stateless JWT Authentication:** Utilizes an optimized JSON Web Token engine that extracts, validates, and serializes user roles (`FREE` vs `PRO`) directly into short-lived access tokens to protect core downstream routes.
* **Dual-Token Lifecycle Architecture:** Integrates database-backed, long-lived `UUID` refresh tokens with automated rotation and transactional cleanup to handle seamless, silent session extensions without password prompts.
* **Server-Side Token Blacklisting (True Logout):** Eliminates standard stateless JWT vulnerabilities by computing token Time-to-Live (TTL) on logout actions, caching signatures inside a database block list to reject subsequent hijacked requests instantly.

### 2. High-Performance Traffic Control (Rate-Limiting)
* **Bucket4j Token Bucket Implementation:** Decoupled completely from core servlet configurations using a high-performance **Spring MVC HandlerInterceptor** running immediately after identity finalization.
* **Dynamic Tier Throttling:** Programmatically allocates individual, thread-safe virtual token buckets mapped to active user context identities:
    * **FREE Tier:** Strictly capped at `10 requests / minute` with instant `429 Too Many Requests` mitigation drop-offs.
    * **PRO Tier:** Accelerated to `100 requests / minute` with an absolute auto-refill interval strategy.

### 3. Defensive Programming & Global Interception
* **Centralized Exception Catchment:** Engineered using a unified `@RestControllerAdvice` to trap multi-threaded security faults (`AccessDeniedException`, custom token failures) before internal servlet forward leak routes can occur.
* **Unified API Response Layouts:** Formats all application errors into predictable, structured JSON objects containing exact HTTP statuses, human-readable feedback, and precise request timestamps.

---

## 🛠️ Technology Stack
* **Framework:** Spring Boot 3.x, Spring Security 6.x, Spring Data JPA
* **Database Engine:** MySQL 8.0
* **Traffic Control Core:** Bucket4j (Token-Bucket Algorithm)
* **Token Handling:** JJWT (Java JWT Library)
* **Build & Dependency Automation:** Maven
* **Containerization Engine:** Docker & Docker Compose

---

## 🐋 Containerized Local Deployment (Docker Architecture)

The entire application ecosystem—including the compiled Spring Boot web container and a pre-configured, isolated MySQL instance—can be deployed on any machine with **one single command** via Docker Compose.

### Prerequisites
Ensure you have the following installed on your machine:
* [Docker Desktop](https://www.docker.com/products/docker-desktop/)
* Git

### Deployment Steps
1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git](https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git)
   cd YOUR_REPOSITORY_NAME