# Infrastructure

## Security & Authentication

### JWT Implementation
- **Token Types**: `ACCESS` and `REFRESH` (enum-based)
- **Algorithm**: HS256 (HMAC-SHA256)
- **Token Location**: Authorization header with "Bearer " prefix

### Filter Chain Order
```
1. JwtExceptionFilter     → Handles JWT-related exceptions
2. JwtAuthenticationFilter → Validates token, sets SecurityContext
3. ApiLogFilter           → Logs request/response
```

### Role-Based Access Control
| Role | Description |
|------|-------------|
| USER | Basic authenticated user |
| ADMIN | Organization administrator |
| OWNER | Organization owner |
| MASTER | System administrator |

### Security Annotations
```java
// Endpoint protection in SecurityConfig
.requestMatchers(HttpMethod.POST, "/organization/invitation").hasAnyRole(ADMIN, OWNER, MASTER)
.requestMatchers(HttpMethod.GET, "/user/list").hasAnyRole(USER, ADMIN, OWNER, MASTER)
```

---

## Exception Handling

### Exception Hierarchy
```
RuntimeException
└── BusinessException (base)
    └── Domain-specific exceptions (e.g., OrganizationNotFoundException)
```

### Per-Domain Error Enum Pattern
```java
// domain/{domain}/exception/{Domain}Error.java
public enum OrganizationError implements ErrorProperty {
    ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Organization not found"),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "Invalid invitation code"),
    LICENSE_LIMIT_EXCEEDED(HttpStatus.CONFLICT, "License limit exceeded");

    private final HttpStatus status;
    private final String message;
}
```

### Singleton Exception Pattern
```java
// domain/{domain}/exception/{Domain}NotFoundException.java
public class OrganizationNotFoundException extends BusinessException {
    public static final OrganizationNotFoundException EXCEPTION = new OrganizationNotFoundException();
    private OrganizationNotFoundException() {
        super(OrganizationError.ORGANIZATION_NOT_FOUND);
    }
}

// Usage in Service
throw OrganizationNotFoundException.EXCEPTION;
```

### Global Exception Handlers
- **Location**: `global/exception/handler/`
- `ExceptionAdvice` - Handles `BusinessException`
- `ValidationExceptionAdvice` - Handles validation errors

---

## Validation

### Request Validation
```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public BaseResponseData<String> register(
        @RequestBody @Valid final RegisterRequest request
) {
    // ...
}
```

### Common Validation Annotations
- `@NotNull`, `@NotBlank` - Required fields
- `@Positive` - Positive numbers
- `@Valid` - Nested object validation

---

## QueryDSL Integration

### Query Repository Pattern
```java
// domain/{domain}/domain/repository/query/{Domain}QueryRepository.java
@Repository
@RequiredArgsConstructor
public class ClientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ClientEntity> findAllClients(int page, int size, String userId, String organizationId) {
        return jpaQueryFactory
            .selectFrom(clientEntity)
            .where(builder)
            .offset((long) (page - 1) * size)
            .limit(size)
            .orderBy(clientEntity.createdAt.desc())
            .fetch();
    }

}
```

### Query Service Pattern
```java
// domain/{domain}/application/query/{Domain}QueryService.java
@Service
@RequiredArgsConstructor
public class ClientQueryService {

    private final ClientQueryRepository queryRepository;

    public List<Client> findAll(int page, int size, String userId, String organizationId) {
        return queryRepository.findAllClients(page, size, userId, organizationId)
            .stream()
            .map(Client::of)
            .toList();
    }

}
```

---

## Pagination

### PageRequest DTO
```java
// global/common/dto/request/PageRequest.java
@Getter
@Setter
public class PageRequest {
    @NotNull @Positive private int page;
    @NotNull @Positive private int size;
}
```

### Usage in API Handler
```java
@GetMapping("/list")
public BaseResponseData<List<ClientResponse>> getClients(
        @ModelAttribute @Valid final PageRequest pageRequest
) {
    return BaseResponseData.ok("Success", useCase.getClients(pageRequest));
}
```

---

## External Service Integrations

### Location
All external service integrations are in `global/infra/`

### Firebase Cloud Messaging (FCM)
- **Location**: `global/infra/fcm/`
- Push notification service
- Token stored in `FcmEntity`

### Google Cloud Storage (GCS)
- **Location**: `global/infra/gcs/`
- File upload/download/delete
- Supported types: WAV, PNG, JPG, JPEG
- URL format: `https://storage.googleapis.com/{bucket}/{uuid}`

### Email Service
- **Location**: `global/infra/email/`
- SMTP-based with `@Async` for non-blocking
- Thymeleaf template support via `MailTemplateRenderer`

### Toss Payments
- **Location**: `global/infra/toss/`
- WebClient-based REST client
- Basic auth with Base64 encoding

---

## Async Configuration

### Thread Pool Settings
```java
@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        return executor;
    }
}
```

### Usage
```java
@Async
public void sendEmail(String to, String subject, String content) {
    // Non-blocking email sending
}
```

---

## API Logging

### ApiLogFilter
- **Location**: `global/log/filter/ApiLogFilter.java`
- Logs all API requests/responses (except `/`, `/index.html`, `/sse`)
- Captures: User ID, URI, HTTP method, client IP, status code, duration, response message

### Log Entity
- Stored in database for audit trail
- Accessible via `/log` endpoint (MASTER role only)

---

## Redis Repository

### Interface
```java
public interface RedisRepository {
    void save(String key, Object value, long timeout, TimeUnit unit);
    <T> T getByKey(String key, Class<T> type);
    void editByKey(String key, Object value, long timeout, TimeUnit unit);
    void delete(String key);
}
```

---

## Configuration Profiles

### Profile Structure
- `application.yml` - Production (secrets from environment variables)
- `application-dev.yml` - Development
- `application-local.yml` - Local development

### Key Configurations
```yaml
# Database
spring.datasource.url: ${secrets.DATABASE_URL}
spring.jpa.hibernate.ddl-auto: none  # prod: none, dev: update

# File Upload
spring.servlet.multipart.max-file-size: 120MB
spring.servlet.multipart.max-request-size: 120MB

# JWT
application.jwt.secret-key: ${secrets.JWT_SECRET}
application.jwt.expiration: 86400000      # 24 hours
application.jwt.refresh-expiration: 604800000  # 7 days
```

### Configuration Properties Pattern
```java
@ConfigurationProperties(prefix = "application.jwt")
public record JwtProperties(
    String secretKey,
    long expiration,
    long refreshExpiration
) {}
```

---

## WebClient Configuration

### Multiple WebClient Beans
```java
// global/config/web/WebClientConfig.java
@Bean
public WebClient sttWebClient() {
    // 5s connection, 3min response timeout
}

@Bean
public WebClient tossWebClient() {
    // 5s connection, 3min response timeout
}
```

---

## Base Classes

### BaseEntity
```java
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
```

### BaseResponse & BaseResponseData
```java
// All API responses use these
BaseResponse.ok("Success message");
BaseResponseData.ok("Success message", data);
BaseResponseData.created("Created message", data);
```

---

## Key File Locations

| Pattern | File Path |
|---------|-----------|
| Security Config | `global/security/config/SecurityConfig.java` |
| JWT Provider | `global/security/jwt/JwtProvider.java` |
| Base Entity | `global/common/entity/BaseEntity.java` |
| Base Response | `global/common/dto/response/BaseResponse.java` |
| Exception Advice | `global/exception/handler/ExceptionAdvice.java` |
| QueryDSL Config | `global/config/query/QueryDslConfig.java` |
| Async Config | `global/config/async/AsyncConfig.java` |
| API Log Filter | `global/log/filter/ApiLogFilter.java` |

---

## API Documentation Format

### Response Structure
Document API responses using table format with the following columns:

| Parameter | Type | Description |
|-----------|------|-------------|
| fieldName | Type | Field description |

### Example Documentation

**Endpoint**: `GET /meeting/{meetingId}`

**Response**:
| Parameter | Type | Description |
|-----------|------|-------------|
| id | String | Meeting ID |
| userId | String | User ID |
| organizationId | String | Organization ID |
| client | Object | Client information |
| client.id | String | Client ID |
| client.name | String | Client name |
| client.email | String | Client email |
| client.phoneNumber | String | Client phone number |
| client.companyName | String | Client company name |
| stt | Object | Speech-to-text information |
| stt.textList | Array | List of transcribed text segments |
| stt.textList[].start | double | Start time (seconds) |
| stt.textList[].end | double | End time (seconds) |
| stt.textList[].word | String | Transcribed word/sentence |
| stt.textList[].speakerId | int | Speaker identifier |
| stt.jobStatus | String | Job status (RUNNING, DONE, ERROR) |
| follow | Object | Follow-up information |
| follow.followType | String | Follow type (NONE, ONLINE, OFFLINE) |
| follow.customTagList | Array | List of custom tags |
| follow.customTagList[].idx | Long | Tag index |
| follow.customTagList[].tag | String | Tag name |
| createdAt | LocalDateTime | Creation timestamp |
| modifiedAt | LocalDateTime | Last modification timestamp |

### Nested Object Notation
- Use dot notation for nested objects: `client.name`
- Use bracket notation for array items: `textList[]`
- Combine for nested arrays: `textList[].word`

### Type Conventions
| Java Type | Documentation Type |
|-----------|-------------------|
| String | String |
| Long, Integer, int | Long, Integer, int |
| double, Double | double |
| LocalDateTime | LocalDateTime |
| List<T> | Array |
| Custom Object | Object |
| Enum | String (with possible values) |
