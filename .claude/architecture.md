# Architecture

## Layer Structure
```
API Handler → UseCase → Service / QueryService → Repository
     ↓           ↓          ↓           ↓             ↓
  Request    Response   Domain      Domain         Entity
    DTO        DTO      Object      Object         (JPA)
```

## Layer Responsibilities

### 1. API Handler (Controller)
- Handles HTTP request/response
- Passes Request DTO to UseCase
- Returns Response DTO to client

### 2. UseCase
- Orchestrates business use cases (Facade pattern)
- Decomposes Request DTO into parameters and calls Service/QueryService
- Converts Domain objects into Response DTOs
- **Only layer where multiple Services can be combined**
- **Only layer where cross-domain orchestration is allowed** — any interaction between two domains (see the Domain Interaction Map below) is expressed here by injecting the other domain's `Service`/`QueryService`, never by Service-to-Service injection

### 3. Service (Command)
- Performs **command** operations only: save, edit, delete
- Returns Domain objects or void
- **Does NOT receive or return Request/Response DTOs**
- **Injects only ONE Repository** (no service-to-service dependency)
- Converts Entity ↔ Domain objects

### 4. QueryService (Query)
- Performs **query** operations only: find, check, exists
- Returns Domain objects
- **Injects only ONE domain's repositories** — its `JpaRepository` and/or `QueryRepository` may be injected together (JpaRepository for derived single-entity finders, QueryRepository for dynamic/paged/projection queries); does NOT inject other domains' repositories
- Follows CQS (Command Query Separation) principle
- **Location**: `application/query/{Domain}QueryService.java`

### 5. Repository
- Database access
- Returns Entity

---

## Object Types

### Request DTO
- **Location**: `domain/{domain}/client/dto/request/`
- **Role**: Transfers API request data
- **Usage**: API Handler → UseCase

### Response DTO
- **Location**: `domain/{domain}/application/response/`
- **Role**: Transfers API response data
- **Usage**: UseCase → API Handler
- **Conversion**: `Response.of(Domain)`

### Domain Object
- **Location**: `domain/{domain}/domain/model/`
- **Role**: Pure business domain concept
- **Characteristic**: No JPA annotations
- **Usage**: Service ↔ UseCase
- **Conversion**: `Domain.of(Entity)`

### Entity
- **Location**: `domain/{domain}/domain/entity/`
- **Role**: Database mapping
- **Characteristic**: Contains JPA annotations
- **Usage**: Repository ↔ Service

---

## Data Flow

### Read Operations (via QueryService)
```
1. API Handler: Receives Request
2. UseCase: Decomposes Request into parameters, calls QueryService
3. QueryService: Queries Entity from Repository
4. QueryService: Converts Entity → Domain object
5. QueryService: Returns Domain object
6. UseCase: Converts Domain → Response DTO
7. API Handler: Returns Response
```

### Write Operations (via Service)
```
1. API Handler: Receives Request
2. UseCase: Decomposes Request into parameters, calls Service
3. Service: Converts parameters → Entity
4. Service: Saves/edits Entity via Repository
5. Service: Converts Entity → Domain object (if needed)
6. Service: Returns Domain object or void
7. UseCase: Converts Domain → Response DTO (if needed)
8. API Handler: Returns Response
```

---

## Code Examples

### Domain Object
```java
// domain/asset/domain/model/Asset.java
@Builder
public record Asset(
    Long id,
    AssetType assetType,
    String itemName
) {
    public static Asset of(AssetEntity entity) {
        return Asset.builder()
            .id(entity.getId())
            .assetType(entity.getAssetType())
            .itemName(entity.getItemName())
            .build();
    }
}
```

### Service (Command only)
```java
// domain/asset/application/service/AssetService.java
@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetJpaRepository repository;

    @Transactional
    public void save(AssetType assetType, String itemName) {
        AssetEntity entity = AssetEntity.builder()
            .assetType(assetType)
            .itemName(itemName)
            .build();
        repository.save(entity);
    }

    @Transactional
    public void edit(Long id, AssetType assetType, String itemName) {
        AssetEntity entity = repository.findById(id)
            .orElseThrow(() -> AssetNotFoundException.EXCEPTION);
        entity.editAsset(assetType, itemName);
    }

    @Transactional
    public void delete(Long id) {
        AssetEntity entity = repository.findById(id)
            .orElseThrow(() -> AssetNotFoundException.EXCEPTION);
        entity.delete();
        repository.save(entity);
    }

}
```

### QueryService (Query only)
```java
// domain/asset/application/query/AssetQueryService.java
@Service
@RequiredArgsConstructor
public class AssetQueryService {

    private final AssetJpaRepository repository;

    public Asset find(Long id) {
        AssetEntity entity = repository.findById(id)
            .orElseThrow(() -> AssetNotFoundException.EXCEPTION);
        return Asset.of(entity);
    }

    public List<Asset> findAll() {
        return repository.findAll().stream()
            .map(Asset::of)
            .toList();
    }

}
```

### UseCase
```java
// domain/asset/application/usecase/AssetUseCase.java
@Component
@RequiredArgsConstructor
public class AssetUseCase {

    private final AssetService assetService;
    private final AssetQueryService assetQueryService;

    public void register(CreateAssetRequest request) {
        assetService.save(
            request.assetType(),
            request.itemName()
        );
    }

    public AssetResponse getById(Long assetId) {
        Asset asset = assetQueryService.find(assetId);
        return AssetResponse.of(asset);
    }

    public List<AssetResponse> getAll() {
        List<Asset> assets = assetQueryService.findAll();
        return assets.stream()
            .map(AssetResponse::of)
            .toList();
    }

    public void update(UpdateAssetRequest request) {
        assetService.edit(
            request.assetId(),
            request.assetType(),
            request.itemName()
        );
    }

    public void delete(Long assetId) {
        assetService.delete(assetId);
    }

}
```

### Response DTO
```java
// domain/asset/application/response/AssetResponse.java
@Builder
public record AssetResponse(
    Long id,
    AssetType assetType,
    String itemName
) {
    public static AssetResponse of(Asset asset) {
        return AssetResponse.builder()
            .id(asset.id())
            .assetType(asset.assetType())
            .itemName(asset.itemName())
            .build();
    }
}
```

---

# Product Architecture

> The sections above define the **layered-architecture conventions** every domain must follow. The sections below describe the **product** those conventions implement: its domains, how they interact, the key flows, and the infrastructure they depend on. The `asset` example above is purely illustrative — the real domains are catalogued below.

## Product Overview

`bigtablet-homepage-server` is the Java 21 / Spring Boot backend for the **Bigtablet corporate homepage**. It exposes public, bilingual (KR/EN) site content — **blog** posts, **news** items, and **job** postings — plus two public visitor actions: submitting a **recruit** (job application) and registering in the **talent** pool. Everything else is a **MASTER-admin back-office**: admins authenticate through Redis-backed email verification and **WebAuthn/FIDO2 passkeys**, receive a stateless **JWT**, and manage content. Recruitment events fan out **email** (SMTP + Thymeleaf, KR/EN templates) and **Slack** notifications; uploaded images live in **Google Cloud Storage**; list/search reads use **QueryDSL** paging; a daily scheduler expires ended job postings.

## Domain Catalog

The codebase is organized into **6 business domains**, plus the cross-cutting **global/security** layer and a **global infra / config / common** layer. Each domain follows the layer conventions above; cross-domain calls happen **only in the UseCase layer**.

| Domain | Purpose | Key Entities | Depends On | External Infra |
|--------|---------|--------------|------------|----------------|
| **admin** | Admin identity & access: account lookup, Redis-backed email verification (send/verify), and **WebAuthn/FIDO2 passkey** register + login (Yubico `RelyingParty`) issuing a JWT. Owns `AdminRole`. | `AdminEntity` (tb_admin), `WebAuthnCredentialEntity` (tb_webauthn), `AdminRole` | global/security | Redis, Email/SMTP, WebAuthn, JWT |
| **blog** | Bilingual blog posts (KR/EN title + LONGTEXT body, image, view counter). Public read/list/title-search + public view increment; admin CRUD. | `BlogEntity` (tb_blog), `Blog` | _(none)_ | GCS (images) |
| **news** | Bilingual news items linking out to an external URL with a thumbnail. Public read/list; admin CRUD. | `NewsEntity` (tb_news), `News` | _(none)_ | GCS (thumbnails) |
| **job** | Job postings (department / location / recruit-type / education enums, KR/EN intro & qualification text, start/end dates, active flag). Public read/list + admin CRUD/deactivate; an active/expired split via QueryDSL; a daily scheduler deactivates postings past `endDate`. | `JobEntity` (tb_job), `Job`, `JobInput`, `Department` / `Location` / `RecruitType` / `Education` | _(none)_ | QueryDSL, `@Scheduled` |
| **recruit** | Job applications: public submit (resolves the target `job`, persists applicant + attachments/portfolio, sends a receipt email + Slack alert), admin list/detail and status transitions (DOCUMENT → interviews → ACCEPTED / REJECTED) with accept/reject emails. | `RecruitEntity` (tb_recruit), `Recruit`, `RecruitInput`, `Status` / `EducationLevel` / `Military` | job | Email, Slack, GCS, QueryDSL |
| **talent** | Talent pool: public self-registration (unique email) with portfolio + extra URLs, admin list/search, an active flag, and an admin "offer" email to a registered talent. | `TalentEntity` (tb_talent), `Talent` | _(none)_ | Email, GCS, QueryDSL |

### Cross-cutting / Global layers

| Layer | Purpose | Key Types | Depends On | External Infra |
|-------|---------|-----------|------------|----------------|
| **global/security** | Stateless JWT auth for admin endpoints; WebAuthn relying-party setup; `CustomUserDetails` / `AdminRole` principal; CORS, CSRF-off, filter ordering (`JwtExceptionFilter` → `JwtAuthenticationFilter`). Selected public endpoints are permitAll; everything else requires authentication. | `CustomUserDetails`, `AdminRole`, `JwtType` | admin | JWT (jjwt HS256), WebAuthn (Yubico), Redis |
| **global/infra + config + common** | Third-party integrations behind Spring beans (GCS, Email/SMTP + Thymeleaf, Slack) plus shared building blocks (BaseEntity auditing, BaseResponse / PageResponse, RedisRepository, QueryDslConfig, AsyncConfig, request-logging filter). | `BaseEntity`, `BaseResponse`, `RedisRepository` | — | GCS, SMTP, Slack, Redis, QueryDSL |

---

## Domain Interaction Map

All edges below are realized **exclusively in the UseCase layer**: a domain's UseCase injects another domain's `Service` / `QueryService`, or a global infra bean. Domains never inject another domain's repository, and Services never call other Services directly. Only one cross-**domain** edge exists (`recruit → job`); the rest are domain → global infra/security.

| From → To | Via | Reason |
|-----------|-----|--------|
| recruit → job | `JobQueryService.find` | Resolve & validate the target job posting when an application is submitted |
| recruit → global/infra | `EmailService` + `MailTemplateRenderer` / `SlackNotifier` | Receipt + status-change emails (KR/EN) and a Slack alert on a new application |
| talent → global/infra | `EmailService` + `MailTemplateRenderer` | "Offer" email to a registered talent |
| admin → global/infra | `EmailService` + Redis (`RedisRepository`) | Email-verification codes; WebAuthn challenge storage |
| admin → global/security | `JwtProvider` / WebAuthn `RelyingParty` | Issue JWT on passkey login; verify registration / assertion |
| all domains → global/security | JWT filter + `CustomUserDetails` | Authenticate admin requests; selected endpoints are public |

---

## Key Flows

### 1. Admin sign-in (email verification + WebAuthn passkey → JWT)
1. `POST /admin/email-verification/send` issues a Redis-stored code to the admin email; `POST /admin/email-verification/verify` marks it verified.
2. `POST /admin/webauthn/register/start|finish` registers a passkey (Yubico `RelyingParty`, challenge in Redis); `POST /admin/webauthn/login/start|finish` verifies the assertion, bumps the signature count, and returns a JWT (`JsonWebTokenResponse`).
3. Every non-public request carries the JWT; `JwtAuthenticationFilter` resolves `CustomUserDetails`; `SecurityConfig` permits the public routes (below) and authenticates the rest.

### 2. Public content + management (blog / news / job)
1. Public: `GET /blog/**`, `GET /news/**`, `GET /job`, `GET /job/list`, and the `PATCH /blog` view-counter are permitAll.
2. Admin (authenticated): create / update / delete blog, news, job; `PATCH /job` deactivate; `GET /job/list/deactivate` for expired postings.
3. `JobScheduler` (daily `0 0 0`, Asia/Seoul) deactivates all postings whose `endDate` has passed.

### 3. Recruitment (apply → notify → triage)
1. `POST /recruit` (public): `RecruitUseCase` validates the job via `JobQueryService`, persists the application + attachments, then sends a receipt email and a Slack alert.
2. Admin: `GET /recruit` / `/recruit/list`, `PATCH /recruit` status update, `PATCH /recruit/accept|reject` — status flows DOCUMENT → FIRST_INTERVIEW → SECOND_INTERVIEW → ACCEPTED / REJECTED, with accept/reject emails.

### 4. Talent pool
1. `POST /talent` (public): self-register with a unique email, portfolio, and extra URLs.
2. Admin: `GET /talent` / `/talent/list` / `/talent/search`, toggle active, and `POST /talent/offer` to email a registered talent.

---

## Infrastructure & Cross-Cutting

| Tech | Purpose | Used By |
|------|---------|---------|
| **Google Cloud Storage (GCS)** | Image storage for blog / news / job / recruit / talent; upload via `/gcp/**` | blog, news, recruit, talent, global/infra |
| **JWT (jjwt HS256) + Spring Security** | Stateless admin auth; public-endpoint allowlist; CORS; CSRF disabled | admin, global/security |
| **WebAuthn / FIDO2** (Yubico) | Passkey register / login for admins | admin, global/security |
| **Redis** | Email-verification codes, WebAuthn challenges | admin, global/security, global/infra |
| **Email / SMTP** (JavaMailSender + Thymeleaf, `@Async`) | Recruitment receipt / accept / reject emails, talent offer (KR/EN templates) | recruit, talent, admin, global/infra |
| **Slack** (`SlackNotifier`) | Alert on a new job application | recruit, global/infra |
| **MySQL** via JPA / Hibernate + QueryDSL | Primary store (BaseEntity auditing); `JPAQueryFactory` for paged list / search | all domains, global |
| **Spring `@Scheduled`** | Daily job-posting expiry | job |
| **Spring `@Async`** | Off-request email / Slack sends | global/config, recruit, talent |

### Security model

- **Stateless JWT** on all non-public endpoints; the principal is a `CustomUserDetails` carrying `AdminRole`.
- **Public (permitAll)**: static resources, `/`, `/index.html`, `OPTIONS /**`, `/admin/email-verification/**`, `/admin/webauthn/register/**`, `/admin/webauthn/login/**`, `GET /job`, `GET /job/list`, `POST /recruit`, `/gcp/**`, `GET /blog/**`, `PATCH /blog`, `GET /news/**`, `POST /talent`.
- **Filter chain**: `JwtExceptionFilter` → `JwtAuthenticationFilter` (registered around `UsernamePasswordAuthenticationFilter`); CSRF disabled, CORS configured.
- **WebAuthn relying party** is configured for admin passkey login.
