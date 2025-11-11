# 아키텍처 가이드라인

## 레이어 구조

```
API Handler → UseCase → Service → Repository
     ↓           ↓          ↓          ↓
  Request    Response   Domain      Entity
    DTO        DTO      Object      (JPA)
```

---

## 계층별 역할

### 1. API Handler (Controller)
- HTTP 요청/응답 처리
- Request DTO를 UseCase에 전달
- Response DTO를 클라이언트에 반환

### 2. UseCase
- 비즈니스 유스케이스 조율
- Request DTO를 파라미터로 분해하여 Service 호출
- Service로부터 받은 Domain 객체를 Response DTO로 변환

### 3. Service
- 비즈니스 로직 수행
- Domain 객체를 반환
- Request/Response DTO를 받거나 반환하지 않음
- Repository와 통신
- Entity ↔ Domain 객체 변환

### 4. Repository
- 데이터베이스 접근
- Entity 반환

---

## 객체 타입

### Request DTO
- 위치: `domain/{domain}/client/request/`
- 역할: API 요청 데이터 전달
- 사용처: API Handler → UseCase

### Response DTO
- 위치: `domain/{domain}/application/response/`
- 역할: API 응답 데이터 전달
- 사용처: UseCase → API Handler
- 변환: `Response.of(Domain)`

### Domain 객체
- 위치: `domain/{domain}/domain/model/`
- 역할: 순수 비즈니스 도메인 개념
- 특징: JPA 어노테이션 없음
- 사용처: Service ↔ UseCase
- 변환: `Domain.of(Entity)`

### Entity
- 위치: `domain/{domain}/domain/entity/`
- 역할: 데이터베이스 매핑
- 특징: JPA 어노테이션 포함
- 사용처: Repository ↔ Service

---

## 규칙

### ✅ DO (해야 할 것)

1. **UseCase에서 Request를 파라미터로 분해**
   ```java
   public void register(CreateAssetRequest request) {
       assetService.save(
           request.assetType(),
           request.major(),
           request.minor(),
           // ...
       );
   }
   ```

2. **UseCase에서 Response 생성**
   ```java
   public AssetResponse getById(Long id) {
       Asset asset = assetService.findById(id);
       return AssetResponse.of(asset);
   }
   ```

3. **Service는 Domain 객체 반환**
   ```java
   @Service
   @RequiredArgsConstructor
   public class AssetService {
       private final AssetRepository repository;

       public Asset findById(Long id) {
           AssetEntity entity = repository.findById(id)
               .orElseThrow();
           return Asset.of(entity);
       }
   }
   ```

4. **Service는 클래스로 작성 (인터페이스 없음)**

### ❌ DON'T (하지 말아야 할 것)

1. **Service에 Request/Response DTO 사용 금지**
   ```java
   // ❌ 잘못된 예
   void authenticate(LoginRequest request);
   AssetResponse findById(Long id);

   // ✅ 올바른 예
   void authenticate(String email, String password);
   Asset findById(Long id);
   ```

2. **Service에서 Response 생성 금지**
   ```java
   // ❌ 잘못된 예
   public AssetResponse findById(Long id) {
       AssetEntity entity = repository.findById(id).orElseThrow();
       return AssetResponse.of(entity);
   }

   // ✅ 올바른 예
   public Asset findById(Long id) {
       AssetEntity entity = repository.findById(id).orElseThrow();
       return Asset.of(entity);
   }
   ```

3. **Service에서 Entity 반환 금지**
   ```java
   // ❌ 잘못된 예
   AssetEntity findById(Long id);

   // ✅ 올바른 예
   Asset findById(Long id);
   ```

---

## 데이터 흐름

### 조회 (Read)
```
1. API Handler: Request 수신
2. UseCase: Request를 파라미터로 분해하여 Service 호출
3. Service: Repository에서 Entity 조회
4. Service: Entity → Domain 객체 변환
5. Service: Domain 객체 반환
6. UseCase: Domain → Response DTO 변환
7. API Handler: Response 반환
```

### 생성/수정 (Write)
```
1. API Handler: Request 수신
2. UseCase: Request를 파라미터로 분해하여 Service 호출
3. Service: 파라미터 → Entity 변환
4. Service: Repository에 Entity 저장
5. Service: Entity → Domain 객체 변환 (필요시)
6. Service: Domain 객체 또는 void 반환
7. UseCase: Domain → Response DTO 변환 (필요시)
8. API Handler: Response 반환
```

---

## 예시 코드

### Domain 객체
```java
// domain/asset/domain/model/Asset.java
@Builder
public record Asset(
    Long id,
    AssetType assetType,
    String itemName,
    // ...
) {
    public static Asset of(AssetEntity entity) {
        return Asset.builder()
            .id(entity.getId())
            .assetType(entity.getAssetType())
            .itemName(entity.getItemName())
            // ...
            .build();
    }
}
```

### Service
```java
// domain/asset/application/service/AssetService.java
@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository repository;

    public Asset findById(Long id) {
        AssetEntity entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        return Asset.of(entity);
    }

    public List<Asset> findAll() {
        return repository.findAll().stream()
            .map(Asset::of)
            .toList();
    }

    @Transactional
    public void save(AssetType assetType, String itemName, ...) {
        AssetEntity entity = AssetEntity.builder()
            .assetType(assetType)
            .itemName(itemName)
            // ...
            .build();
        repository.save(entity);
    }

    @Transactional
    public void update(Long id, AssetType assetType, ...) {
        AssetEntity entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        entity.updateAsset(assetType, ...);
    }

    @Transactional
    public void delete(Long id) {
        AssetEntity entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        entity.delete();
        repository.save(entity);
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

    public void register(CreateAssetRequest request) {
        assetService.save(
            request.assetType(),
            request.itemName(),
            // ...
        );
    }

    public AssetResponse getById(Long assetId) {
        Asset asset = assetService.findById(assetId);
        return AssetResponse.of(asset);
    }

    public List<AssetResponse> getAll() {
        List<Asset> assets = assetService.findAll();
        return assets.stream()
            .map(AssetResponse::of)
            .toList();
    }

    public void update(UpdateAssetRequest request) {
        assetService.update(
            request.assetId(),
            request.assetType(),
            request.itemName(),
            // ...
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
    String itemName,
    // ...
) {
    public static AssetResponse of(Asset asset) {
        return AssetResponse.builder()
            .id(asset.id())
            .assetType(asset.assetType())
            .itemName(asset.itemName())
            // ...
            .build();
    }
}
```

---

## 체크리스트

### Domain 객체 생성
- [ ] `domain/{domain}/domain/model/` 디렉토리 생성
- [ ] Domain record 클래스 작성
- [ ] `static Domain of(Entity entity)` 메서드 구현

### Service 수정
- [ ] Service에서 Entity/Response 타입 → Domain 타입으로 변경
- [ ] Request 파라미터를 개별 파라미터로 분해
- [ ] Service에서 Entity → Domain 변환 로직 추가
- [ ] Service에서 파라미터 → Entity 생성 로직 추가

### UseCase 수정
- [ ] Service 호출 시 Request를 파라미터로 분해
- [ ] Service에서 받은 Domain을 Response로 변환
- [ ] Response 생성은 UseCase에서만 수행

### Response 수정
- [ ] `Response.of(Entity)` → `Response.of(Domain)` 변경
- [ ] Domain 객체를 받아 Response 생성

---

## 마이그레이션 순서

1. **Domain 객체 생성**
   - Entity를 참고하여 Domain record 작성
   - `of(Entity)` 정적 팩토리 메서드 작성

2. **Service 수정**
   - 반환 타입을 Entity/Response → Domain으로 변경
   - Request 파라미터를 개별 파라미터로 분해
   - Entity → Domain 변환 로직 추가 (`Asset.of(entity)`)
   - 파라미터 → Entity 생성 로직 유지

3. **Response 수정**
   - `of(Entity)` → `of(Domain)` 시그니처 변경
   - 내부 로직 수정

4. **UseCase 수정**
   - Service 호출부 수정 (Request 분해)
   - Domain → Response 변환 추가

5. **기존 Service 인터페이스 및 Impl 삭제**
   - Service 인터페이스 삭제
   - ServiceImpl 삭제
   - Service 클래스로 통합

6. **컴파일 및 테스트**
   - 빌드 오류 해결
   - 기능 테스트 수행
