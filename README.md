# SkillNest Backend

Backend API for the **SkillNest** online learning platform.  
Provides RESTful endpoints for user management, courses, lessons, enrollments, progress tracking, and secure authentication.

## ✨ Features

- User registration & authentication (JWT)
- Role-based access control (Student, Instructor, Admin)
- Course & lesson CRUD operations
- Enrollment and learning progress tracking
- File upload support for lesson materials
- REST API with OpenAPI/Swagger documentation
- Containerized with Docker
- Cloud-ready (AWS ECS Fargate deployment)

## 🛠 Tech Stack

| Layer              | Technology                          |
|--------------------|-------------------------------------|
| Language           | Java 21                             |
| Framework          | Spring Boot 3                       |
| Security           | Spring Security + JWT               |
| ORM / Persistence  | Spring Data JPA + Hibernate         |
| Database           | PostgreSQL                          |
| API Documentation  | OpenAPI 3 / Swagger UI              |
| Container          | Docker                              |
| Cloud              | AWS ECR • ECS (Fargate) • ALB       |
| Build Tool         | Maven                               |

## 📂 Project Structure
src/main/java/com/example/skillnest/
- config          → Security, JWT, CORS, OpenAPI config
- controller      → REST endpoints
- dto             → Data Transfer Objects
- mapper          → DTO ↔ Entity mapping (MapStruct or manual)
- model/entity    → JPA entities
- repository      → Spring Data JPA repositories
- service         → Business logic
- exception       → Custom exceptions & handlers
- utils           → Helpers, constants, etc.
- validation      → Custom validate for inputs.

## 🚀 Quick Start (Local)

**Prerequisites**
- Java 21
- Maven 4.0
- PostgreSQL

**Steps**
1. Clone & enter directory

    ```bash
    git clone https://github.com/thaodinh97/SkillNest_backend.git
    cd SkillNest_backend
    ```

2. Create .env file or export variables
    ```env
    DB_URL=
    DB_USERNAME=
    DB_PASSWORD=
    JWT_SECRET=
    CORS_ALLOWED_ORIGINS=
    CLOUDINARY_CLOUD_NAME=
    CLOUDINARY_API_KEY=
    CLOUDINARY_API_SECRET=
    ```
3. Run server

    ```bash
    ./mvnw spring-boot:run
    # or Windows:
    mvnw spring-boot:run
    ```
4. Open in browser
    - API Base: http://localhost:8080

## 🐳 Docker ##
```bash
# Build image
docker build -t skillnest-backend:latest .

docker run --rm -p 8080:8080 \
  --env-file .env \
  skillnest-backend:latest
```

## ☁ Deployment (AWS) ##
Deployed via:
- Container Registry → AWS ECR
- Orchestration         → AWS ECS Fargate
- Load Balancer         → Application Load Balancer
- DNS + SSL             → ACM

Infrastructure-as-code & CI/CD pipeline: SkillNest_infra_deploy
