# Java Code Style

## Basic Principles
- Based on Google Java Style Guide
- Indentation: 1 tab (4 spaces)
- Column limit: 120 characters
- Wildcard imports prohibited (`java.util.*` not allowed)
- `var` type inference prohibited

---

## Import Ordering
```
java.*
javax.*
org.*
com.*

(blank line)

static imports
```
- Follow IntelliJ default import ordering
- Each group separated by blank line
- Alphabetical within each group

---

## Naming Conventions
| Target | Style | Example |
|--------|-------|---------|
| Class | UpperCamelCase | `AuthServiceImpl` |
| Method | lowerCamelCase (verb) | `saveUser` |
| Constant | CONSTANT_CASE | `MY_NUMBER` |
| Field/Parameter | lowerCamelCase | `userName` |
| Boolean variable | `is` prefix | `isChecked` |

---

## Brace Rules
```java
public class Example {

    public String testMethod() {
        int a = 1;
        if (a != 0) {
            try {
                testMethod();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "test";
        } else if (a == 1) {
            return "fail";
        } else {
            return "1234";
        }
    }

    public void methodWithManyParams(
        String parameter1,
        String parameter2,
        String parameter3
    ) {}

    public void blankMethod() {}

}
```

---

## Blank Line Rules
- After class declaration: blank line
- Before closing brace: blank line
- Between methods: 1 blank line
- **Inside methods: NO blank lines**

---

## Operators
- 1 space before and after all operators: `a = 1`, `b != 1`

---

## Comments

**General Rules**
- `/** */` (Javadoc): **Only** for public/protected class, method, field API documentation
- `/* */` (Block comment): Multi-line implementation comments (2+ lines)
- `//` (Line comment): Single-line implementation comments, 1 space after `//`, place directly above related code
- **Do NOT use `/** */` for implementation comments** — it causes Javadoc tooling issues

**Javadoc for Methods**
- Use Javadoc (`/** */`) for public/protected methods
- Private methods: Use `//` for simple explanations
- Include `@param`, `@return` tags
- `@param` does NOT include the type (type is already in the signature)
- `@return` is omitted for void methods

**Javadoc Format**
```java
/**
 * Method description
 * @param paramName Parameter description
 * @return Return value description
 */
public ReturnType methodName(ParamType paramName) {
    // ...
}
```

**Example**
```java
/**
 * GCS 파일 정보 저장
 * @param id 파일 ID (UUID)
 * @param fileUrl 파일 URL
 * @param contentType 파일 타입
 * @param durationMinutes 오디오 파일 길이 (분 단위, 오디오가 아니면 null)
 */
@Transactional
public void save(String id, String fileUrl, String contentType, Long durationMinutes) {
    // ...
}
```

**Field Comments**
- Use `//` above field for implementation notes
```java
// 크레딧 (분 단위, null이면 크레딧 없음)
private Long credit;
```

---

## Long Type
- Uppercase suffix: `300000L`

---

## Annotation Ordering

**By length (shortest → longest), alphabetical if equal**
```java
@Entity
@Getter
@SuperBuilder
@Table(name = "tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    // ...
}
```

---

## DTO Rules

**Use Record Classes**
```java
public record Json(
        String videoId,
        String groupId,
        String sessionId,
        Occupancy occupancy,
        List<TimeLine> timeLine,
        DwellTime dwellTime
) {
    public static Json to(JsonEntity entity) {
        return new Json(
                entity.getVideoId(),
                entity.getGroupId(),
                entity.getSessionId(),
                entity.getOccupancy(),
                entity.getTimeLine(),
                entity.getDwellTime()
        );
    }
}
```
- Entity → DTO conversion method name: `to`
- Entity → Domain conversion method name: `of`
- Domain → Response conversion method name: `of`

---

## Other Rules

### Exception Handling
- Custom per domain: `UserNotFound`, `FileNotFound`

### Enum
- Uppercase, use `_` for separation: `USER_NOT_FOUND`

### Parameter Naming
- Request DTO: `request`
- Entity: `entity`
- If 2+ of same type: use class name

### Constructor Injection
```java
private final VideoJpaRepository videoJpaRepository;
```

### Overriding
- Always add `@Override` (omit if parent is Deprecated)
