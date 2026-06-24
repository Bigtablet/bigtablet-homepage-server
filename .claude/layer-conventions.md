# Layer-specific Conventions

## ApiHandler (Controller)

**Annotations**
```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/{domain}")
```

**Request URI Rules**
- Match domain name: `/auth`
- List returns: append `/list`
- Search API: append `/search`

**Request Rules**
- All parameters require `final`
- POST: Prefer `@RequestBody` (use `@RequestParam` if ≤1 parameter)
- GET: Prefer `@RequestParam` (use `@ModelAttribute` if ≥2 parameters)
- Others: `@RequestParam` if ≤1, `@RequestBody` if ≥2

**Response Rules**
```java
@PostMapping("/sign-in")
public BaseResponseData<JsonWebTokenResponse> signIn(
        @RequestBody @Valid final SignInRequest request
) {
    return BaseResponseData.ok(
            "Login successful",
            authUseCase.signIn(request)
    );
}
```
- Use `BaseResponse` / `BaseResponseData`
- Only in Controller (prohibited in Service layer)

---

## UseCase

**When to use**: When 2+ domains interact, or when Service + QueryService need to be combined

**Role**: Facade pattern — orchestrates multiple Services/QueryServices

**Annotations**
```java
@Component
@RequiredArgsConstructor
```

---

## Service (Command)

**Rules**
- **CQS**: Only command operations (save, edit, delete)
- Inject only one Repository (no service-to-service dependency)
- One method = one responsibility
- **Return Domain objects or void, NOT DTOs**
- Query operations go in QueryService

**Annotations**
```java
@Service
@RequiredArgsConstructor
```

---

## QueryService (Query)

**Rules**
- **CQS**: Only query operations (find, check, exists)
- Inject only ONE domain's repositories — its `JpaRepository` and/or its `QueryRepository` may be injected **together** (the QueryDSL idiom: JpaRepository for derived single-entity finders, QueryRepository for dynamic/paged/projection queries). Do NOT inject another domain's repository.
- Returns Domain objects
- **Location**: `application/query/` (NOT `application/service/`)

**Annotations**
```java
@Service
@RequiredArgsConstructor
```

---

## Repository

**Naming**
- Repository: `{DomainName}JpaRepository`
- Entity: `{TableName/DomainName}Entity`

**Rules**
- Extend JpaRepository
- All columns `private`
- No `@Setter` (except when necessary)
- All Entities extend `BaseEntity`

---

## Method Naming Conventions by Layer

| Action | UseCase | Service | QueryService | Entity |
|--------|---------|---------|-------------|--------|
| Create | `register` | `save` | - | - |
| Read (single) | `get` | - | `find` | - |
| Read (list) | `getAll`, `getList` | - | `findAll`, `findList` | - |
| Update | `update` | `edit` | - | `edit{Field}` |
| Delete | `delete` | `delete` | - | `delete` |
| Validate | `check` | - | `check`, `exists` | - |

### Examples
```
UseCase.update()       → Service.edit()        → Entity.editUser()
UseCase.getById()      → QueryService.find()
UseCase.register()     → Service.save()
UseCase.delete()       → Service.delete()       → Entity.delete()
UseCase.checkExists()  → QueryService.exists()
```
