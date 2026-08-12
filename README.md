# bigtablet-homepage-server

빅태블릿 홈페이지 백엔드 서버 (Spring Boot / MySQL / Redis).

## 빌드

```bash
./gradlew build
```

로컬 빌드 캐시와 병렬 빌드는 `gradle.properties`에서 기본 활성화되어 있다.

### 원격 빌드 캐시 (옵트인, 사내망 전용)

사내망(LAN)에서 `~/.gradle/gradle.properties`에 아래 한 줄을 추가하면 r240 원격 빌드 캐시(팀원 간 컴파일 결과 공유)를 사용한다.

```properties
bigtabletBuildCacheUrl=http://192.168.88.240:5071/cache/
# (선택) 캐시 쓰기까지 켜려면 — 기본은 read-only
# bigtabletBuildCachePush=true
```

## 로컬 개발

로컬 MySQL·Redis 는 docker compose 로 띄운다.

```bash
# 1. 의존 서비스 기동 (MySQL 8.0 + Redis 7)
docker compose -f docker-compose.local.yml up -d

# 2. 로컬 프로파일 설정 복사 (compose 기본값과 일치, 필요 시 수정)
cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml

# 3. 서버 실행
./gradlew bootRun --args='--spring.profiles.active=local'
```

- 접속 정보(로컬 전용): MySQL `localhost:3306`, DB `homepage`, 계정 `homepage`/`localdev` · Redis `localhost:6379`, 비밀번호 `localdev`
- 이 레포는 Flyway 마이그레이션이 없으므로, local 프로파일은 `ddl-auto: update` 로 빈 DB 에 스키마를 생성한다 (운영은 `none`).
- 완전한 기동에는 `.gitignore` 된 GCP credential JSON 이 `src/main/resources/` 에 추가로 필요하다 (팀 시크릿 저장소에서 수령). 자세한 내용은 `application-local.yml.example` 상단 주석 참고.
- 초기화가 필요하면 `docker compose -f docker-compose.local.yml down -v` (볼륨까지 삭제).
