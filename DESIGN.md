## Book Review Service - Design Document

### Overview
The Book Review Service is a Spring Boot application that provides RESTful APIs for managing books, user accounts, reviews, and user favorites. It implements JWT-based authentication and role-based authorization, persistence with JPA/Hibernate, schema migrations with Flyway, and API docs via OpenAPI/Swagger.

### Goals
- Enable CRUD for books, reviews, and favorites
- Secure endpoints with JWT and roles (USER, ADMIN)
- Provide paginated search and rating aggregation
- Support spec-driven development with OpenAPI

### High-Level Architecture
```mermaid
graph TD
  subgraph Client
    UI[Web/SPA/REST Client]
  end

  subgraph Service[BookReviewService (Spring Boot)]
    SEC[Security Filter Chain\nJwtAuthenticationFilter]
    CTRL[REST Controllers\n(Book, Review, User, Favorite)]
    SVC[Services\n(Book, Review, User, Favorite)]
    REPO[JPA Repositories\n(Book, Review, User, FavoriteBook)]
    ENT[Entities/DTOs]
    CONF[Config\n(Security, CORS, JwtUtil)]
    API[OpenAPI/Swagger]
  end

  DB[(PostgreSQL)]

  UI -->|HTTP/JSON + Bearer JWT| SEC
  SEC --> CTRL
  CTRL --> SVC
  SVC --> REPO
  REPO --> DB
  CONF --> SEC
  CONF --> API
```

### Component Responsibilities
- SecurityConfig: Configures CORS, disables CSRF for APIs, declares `SecurityFilterChain`, and wires `JwtAuthenticationFilter`. Public endpoints: `/api/users/register`, `/api/users/login`, `/v3/api-docs/**`, `/swagger-ui/**`. Secured endpoints: `/api/books/**`, `/api/reviews/**` require roles USER or ADMIN; other endpoints require authentication.
- JwtAuthenticationFilter: Extracts and validates Bearer token; sets `SecurityContext` with `ROLE_{role}` authority.
- JwtUtil: Generates and validates JWT with subject (username) and `role` claim. HS256 with Base64 key from `bookreview.jwt.secret`.
- Controllers:
  - BookController: CRUD, pagination (`/page`), keyword search (`/search`), update aggregated rating.
  - ReviewController: CRUD reviews, list by book/user; triggers rating recalculation.
  - UserController: Register, login (returns `{ user, token }`), CRUD, logout message.
  - FavoriteBookController: Nested per user `/api/users/{userId}/favorites` for mark/unmark and list.
  - FavoriteBookCrudController: Flat CRUD at `/api/favorites` for admin/ops use.
- Services:
  - BookServiceImpl: Maps DTOs, persists books, pagination/search, updates `avgRating` and `reviewCount`.
  - ReviewServiceImpl: Persists reviews, recalculates and updates book rating/count after changes.
  - UserServiceImpl: Registers with BCrypt, validates credentials, maps DTOs.
  - FavoriteBookServiceImpl: Mark/unmark favorites and list by user; ensures uniqueness per user/book.
- Persistence:
  - Repositories: Spring Data JPA repositories for entities.
  - Entities & DTOs: Mapped models (`Book`, `Review`, `User`, `FavoriteBook`) and transport DTOs.
- Database/Migrations: PostgreSQL with Flyway migrations under `classpath:db/migration`.
- Observability: Structured logging via SLF4J; log levels configured in `application.properties`.
- API Docs: Springdoc OpenAPI at `/v3/api-docs` and Swagger UI at `/swagger-ui.html`.

### Data Model (conceptual)
- User: id, username, password (BCrypt), role, firstname, lastname
- Book: id, title, author, description, coverImage, genres, publishedYear, avgRating, reviewCount
- Review: id, rating, comment, createdAt, modifiedAt, book_id, user_id
- FavoriteBook: id, user_id, book_id (unique per user/book)

### Security Design
- Authentication: JWT Bearer tokens, generated on `/api/users/login`.
- Authorization: Role-based via Spring Security. Controller access constrained by `SecurityConfig`.
- Token Contents: `sub` (username), `role`, `exp`. Key from `bookreview.jwt.secret`.
- Passwords: Stored using BCrypt hash. Login verifies with `BCryptPasswordEncoder.matches`.
- CORS: Allows `http://localhost:3000` with common methods and credentials for local dev.

### Non-Functional Requirements
- Scalability: Stateless JWT enables horizontal scaling; DB as single writable instance (can scale reads with replicas). Flyway manages schema evolution.
- Performance: Pagination for book listing and search; rating aggregation computed server-side and stored on `Book` to avoid heavy read-time aggregation.
- Security: JWT validation, role checks, BCrypt for passwords. Secrets externalizable via environment.
- Reliability: Spring Boot + JPA transactional semantics. Idempotent deletes and guarded favorite creation.
- Maintainability: Layered architecture (Controller → Service → Repository), DTO mapping, clear logging.
- Observability: Log correlation possible with additional MDC if needed; current logs at INFO.
- Testability: Layers lend to unit tests; integration tests via Spring Boot test and H2.

### Tech Stack
- Language/Runtime: Java 17
- Framework: Spring Boot 3.2.x (Web, Security, Data JPA)
- Auth: JWT (jjwt 0.11.5), BCrypt
- Database: PostgreSQL; H2 for tests
- Migrations: Flyway
- API Docs: springdoc-openapi
- Build: Maven

### Key Endpoints (representative)
- Public: `POST /api/users/register`, `POST /api/users/login`, OpenAPI & Swagger routes
- Secured (USER/ADMIN):
  - Books: `GET /api/books`, `GET /api/books/{id}`, `POST /api/books`, `PUT /api/books/{id}`, `DELETE /api/books/{id}`, `GET /api/books/page`, `GET /api/books/search`
  - Reviews: `POST /api/reviews`, `PUT /api/reviews/{id}`, `DELETE /api/reviews/{id}`, `GET /api/reviews/book/{bookId}`, `GET /api/reviews/user/{userId}`
  - Favorites: `POST /api/users/{userId}/favorites`, `GET /api/users/{userId}/favorites`, `DELETE /api/users/{userId}/favorites/{bookId}`, plus CRUD at `/api/favorites`

### Deployment & Environments
- Config via `application.properties` and profiles (`dev`, `prod`).
- Default ports: Service 8080; Postgres per env.
- Containerization: Add Dockerfile and docker-compose as next steps; secrets via env vars.

### Open Issues / Enhancements
- Improve login by querying `UserRepository.findByUsername` instead of scanning all users.
- Add token blacklist/refresh for logout and long-lived sessions.
- Add validation annotations to DTOs and method params.
- Add request/response schemas to OpenAPI and generate clients/docs.
- Introduce global exception handling (`@ControllerAdvice`).
- Add indexes for frequently queried fields (e.g., `Book.title`, `Book.author`, `FavoriteBook(user, book)`).
- Add rate limiting and audit logging for security-sensitive endpoints.

### Sequence Example: Add Review
1) Client calls `POST /api/reviews` with JWT.
2) `JwtAuthenticationFilter` authenticates; controller delegates to service.
3) `ReviewServiceImpl.addReview` persists review, updates `Book.avgRating` and `reviewCount` via repository.
4) Response returns `ReviewDTO`; subsequent GETs reflect new rating/count.


