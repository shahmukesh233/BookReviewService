## Book Review Service — Task Breakdown

### How to use this file
- Work top-to-bottom by phase. Each task has acceptance criteria and a ready-to-copy prompt for Copilot/Cursor.
- Keep commits small and frequent. After each task, run build/tests and update docs.

### Phase 0 — Repo hygiene and Dev UX
1) Add EditorConfig and basic code style
- Acceptance: `.editorconfig` committed; matches Java style; no reformat churn.
- Prompt:
```text
Create a root .editorconfig for Java 17 Spring projects with UTF-8, LF, 4-space indents, and trimming trailing whitespace.
```

2) Add global exception handling
- Acceptance: `@ControllerAdvice` maps common exceptions to JSON problem responses; errors documented.
- Prompt:
```text
Add a GlobalExceptionHandler using @ControllerAdvice that returns RFC-7807-like JSON for IllegalArgumentException, NoSuchElementException, MethodArgumentNotValidException, and a fallback handler.
```

### Phase 1 — Specifications and API docs
3) Define OpenAPI spec for all controllers
- Acceptance: OpenAPI YAML covering Users, Books, Reviews, Favorites; schemas for DTOs; auth security scheme; served at `/v3/api-docs` and Swagger UI works.
- Prompt:
```text
Create openapi.yaml in src/main/resources with components/schemas for UserDTO, BookDTO, ReviewDTO, FavoriteBookDTO; paths for user register/login/CRUD, book CRUD/search/page, review CRUD/list, favorites endpoints; add JWT bearer security scheme; wire springdoc to load it.
```

4) Add DTO validation annotations
- Acceptance: DTOs use javax/jakarta validation; invalid requests return 400 with validation details.
- Prompt:
```text
Add validation annotations to DTOs: @NotBlank for strings, @Min/@Max for rating, @NotNull where required. Ensure controllers use @Valid.
```

### Phase 2 — Security hardening
5) Improve login path and repository methods
- Acceptance: `UserRepository#findByUsername(String)` exists; login uses it instead of scanning; unique index on username via Flyway.
- Prompt:
```text
Add findByUsername to UserRepository and use it in UserServiceImpl.login. Create a Flyway migration adding a unique index on users.username.
```

6) Add roles and endpoint authorization review
- Acceptance: Read-only endpoints allowed to USER; mutating endpoints restricted appropriately; explicit role checks where needed.
- Prompt:
```text
Review SecurityConfig to ensure GET /api/books and review listing are ROLE_USER or ADMIN, and write/update/delete endpoints require proper roles. Update ant matchers accordingly.
```

### Phase 3 — Persistence and performance
7) Add indexes and query optimizations
- Acceptance: Flyway migration adds indexes on Book(title), Book(author), FavoriteBook(user_id, book_id unique), Review(book_id), Review(user_id).
- Prompt:
```text
Create a Flyway migration adding performant indexes for frequent queries and a unique constraint for favorites (user_id, book_id).
```

8) Paginated queries for reviews and favorites
- Acceptance: Service/repository methods support pagination; controllers expose page/size; defaults preserved.
- Prompt:
```text
Add Pageable support to review listing by bookId and by userId, and to favorites by user; expose page/size query params in controllers.
```

### Phase 4 — Business logic quality
9) Consolidate rating aggregation
- Acceptance: Average rating and count updated transactionally on review create/update/delete; reuse single service method; covered by tests.
- Prompt:
```text
Refactor ReviewServiceImpl to compute and persist Book.avgRating and reviewCount via a single private method called from add/update/delete, using repository aggregation queries when possible.
```

10) Input sanitization and limits
- Acceptance: Enforce reasonable max lengths (title, comment), allowed rating range (1-5), and description length; documented in OpenAPI.
- Prompt:
```text
Add @Size limits to DTOs and reflect constraints in OpenAPI schemas. Ensure rating is 1..5.
```

### Phase 5 — Testing
11) Unit tests for services
- Acceptance: JUnit tests cover BookService, ReviewService, UserService, FavoriteBookService happy paths and edge cases.
- Prompt:
```text
Add JUnit 5 tests with Mockito for service classes, covering success and not-found scenarios; verify repository interactions.
```

12) Integration tests with H2
- Acceptance: Spring Boot tests start with H2, Flyway migrations applied, REST endpoints tested, security enforced, OpenAPI path reachable.
- Prompt:
```text
Add @SpringBootTest + @AutoConfigureMockMvc tests using H2; load Flyway migrations; cover auth, CRUD flows, and validation errors.
```

### Phase 6 — DevOps and deployment
13) Dockerization
- Acceptance: `Dockerfile` for app; `docker-compose.yml` for app+Postgres; healthchecks; env-based config; README updated.
- Prompt:
```text
Add Dockerfile (temurin:17-jre base) and docker-compose with Postgres 16. Wire env vars for DB URL and JWT secret. Document usage.
```

14) CI pipeline
- Acceptance: GitHub Actions workflow runs build, tests, and produces artifact; caches Maven deps.
- Prompt:
```text
Create .github/workflows/ci.yml that checks out code, sets up Java 17, caches Maven, runs mvn -B verify, and uploads surefire reports.
```

### Phase 7 — Documentation and supportability
15) Enhance DESIGN.md with sequence/ER diagrams
- Acceptance: Add ER diagram, auth sequence, and link to rendered Mermaid assets in /diagrams.
- Prompt:
```text
Extend DESIGN.md with ER diagram and an authentication sequence diagram. Export updated SVGs to diagrams/.
```

16) Postman collection and examples
- Acceptance: Provide a Postman/Thunder Client collection with authorization pre-request script for JWT; sample requests.
- Prompt:
```text
Add a Postman collection exporting all endpoints with JWT auth; include example bodies and environment variables.
```

### Stretch goals
- Token refresh/blacklist
- Rate limiting (e.g., Bucket4j) for auth endpoints
- Auditing createdAt/modifiedAt via JPA listeners

### Definition of Done (per phase)
- Builds pass: `mvn -B verify`
- Tests passing with coverage trend up
- Updated docs: `DESIGN.md`, `README.md`, OpenAPI
- Lint/style checks clean; no new critical warnings


