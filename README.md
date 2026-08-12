# bigtablet-homepage-server

## 빌드

```bash
./gradlew build
```

로컬 빌드 캐시와 병렬 빌드는 `gradle.properties`에서 기본 활성화되어 있다.

### 원격 빌드 캐시 (옵트인, 사내망 전용)

사내망(LAN)에서 `~/.gradle/gradle.properties`에 아래 한 줄을 추가하면 r240 원격 빌드 캐시(팀원 간 컴파일 결과 공유)를 사용한다.

```properties
bigtabletBuildCacheUrl=http://192.168.88.240:5071/cache/
```
