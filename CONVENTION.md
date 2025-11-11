# 목차

- :java: [JAVA](https://www.notion.so/2785b41f8384806ba83ac21522316958?pvs=21)
- :springboot: **[SPRING BOOT](https://www.notion.so/2785b41f8384806ba83ac21522316958?pvs=21)**
- ⛔ **[ETC](https://www.notion.so/2785b41f8384806ba83ac21522316958?pvs=21)**

---

# :java: Java

> [**Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)를 참고하여 작성됨.**
>

---

## 용어 정리

- **클래스** : 일반적인 `class`와 `Enum`, `Interface`, `Annotation`을 총칭한다.
- **멤버** : `inner class`, `field`, `method`, `constructor` 같은 클래스 구성 요소를 총칭한다.
- 주석 : 주석은 구현 주석을 의미한다. 문서화 주석은 `README.md` 같은 별도의 파일에서 다룬다.

---

## 소스코드 기본 원칙

### PACKAGE문

1. 특별한 사유가 없는 한 기본적으로 파일 최상단에 위치 시킨다.
2. 개행하지 않으며 따로 열 제한을 두지 않는다.

### IMPORT문

1. 특별한 사유가 없는 한 기본적으로 파일에서 PACKAGE문에서 한 줄 띄운 후 위치 시킨다
2. 와일드 카드로 불러오지 않는다. 반드시 각각 따로 명시한다.

   예) `java.util.*` ← 이처럼 와일드 카드를 사용하지 않는다.

3. 개행하지 않으며 따로 열 제한을 두지 않는다

### 괄호

1. 모든 괄호는 안에 내용이 1줄 이상 있다면 절대로 생략하지 않는다.
2. 괄호 블록 안에 내용이 있는 경우
    1. 여는 중괄호 전에는 개행하지 않고 한 칸의 공백을 둔다.
    2. 여는 중괄호 뒤에는 개행한다.
    3. 닫는 중괄호 전에는 개행한다.
    4. 닫는 중괄호의 개행은 중괄호가 끝나거나 생성자, 메소드, 클래스가 끝날 때 개행한다. 즉 `else`나 `,` 앞에서는 개행하지 않는다.
    5. 여는 소괄호는 앞에 한 칸에 공백을 둔다. 다만 아래 두 경우는 예외로 둔다.
        1. 소괄호가 메서드 이름 앞에 붙는 경우 앞에 공백을 두지 않으며 따로 개행하지 않는다.
        2. 소괄호가 호출하는 함수 앞에 붙는 경우 앞에 공백을 두지 않는다.
    6. 소괄호가 붙는 함수가 3개 이상의 파라미터를 가지고 있을 경우 `,` 을 기준으로 개행하며 닫는 소괄호도 개행한다.
    7. 닫는 소괄호는 코드의 가독성을 향상시키는 방향으로 개행하기도 개행하지 않기도 한다.
       예) 개행 했을 때 코드가 길어진다면 개행하지 않으나 반대로 개행해도 코드 길이가 늘어나지 않고 가독성 향상에 도움이 될 경우 개행함.
3. 괄호 블록 안이 비워져 있는 경우 해당 블록은 개행하지 않는다.

예)

```java
public class Exemple {

	public String testMethod() {
		int a = 1;
		if (a!=0) {
			try {
				testMethod();
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			return "test";
		} else if (a = 1) {
			return "fail";
		} else {
			return "1234";
		}
	}
	getTestMethod(
		"parameter1",
		"parameter2",
		"parameter3"
	);
	public void blankMethod() {}
	
}
```

### 들여쓰기

들여쓰기는 기본적으로 1탭 (Tab)을 원칙으로 하며 4칸의 공백과 동일하다.

### 줄바꿈

줄바꿈은 더 높은 구문 수준에서 끊어주는 것을 원칙으로 한다.

1. 연산자 같은 상징들 앞에서 끊어준다. `.` , `::`,  `&`, `|` 같은 상징들 앞에서는 줄바꿈한다.
2. 줄바꿈을 하고 나면 들여쓰기를 한다.

### 세로 정렬

세로 정렬은 하지 않는 것을 원칙으로 한다.

### 변수 선언

1. 모든 변수 선언은 한 번에 한 개만 한다 `int a, b;` 식으로 하지 않는다.
2. 지역 변수는 처음 사용 되는 지점에 가깝게 선언한다.

### 배열

1. 배열의 선언은 “block-like” 하게 하며 모두 허용된다.
2. C style 배열 선언은 금지이며 `[]`는 타입에 붙여준다.

   예) `String args[]`


### 주석

1. 2줄 이상의 주석에 경우 `/** */` 를 사용한다. 시작하는 부분과 끝 부분을 반드시 개행한다.
2. 그 외 한 줄 주석은 `//` 을 사용한다. 문장을 시작할 때 한 칸의 공백을 두고 작성한다. 항상 해당하는 코드 바로 윗줄에 작성하며 필요 시 가독성을 위해 주석 바로 윗 줄에 빈 줄 하나를 둘 수 있다.
3. JAVA 클래스/파일이 아닌 경우 `#` 같이 해당 파일에 주석 방식을 따른다. 단 2줄 미만인 주석은 2번 규칙에 공백 수칙을 준수하며 작성한다.

### LONG 타입

long은 항상 접미사를 대문자로 한다.

예) `300000L`

### 연산자

1. 연산자는 산술, 부호, 증감, 비교, 논리, 비트,. 대입, 삼항 모두를 총칭하는 말이다.
2. 모든 연산자는 기본적으로 앞 뒤에 한 칸씩 공백을 둔다.

   예) `a = 1` / `b != 1` / `n = (3 > 2) ? 10 : 20;`


### 빈줄

1. 클래스를 선언하고 나고 개행된 첫 줄과 닫는 중괄호 바로 윗 줄은 비워둔다.
2. 클래스 내부에 있는 메서드들 끼리 구분을 위해 한 줄 씩 비워둔다.

   예)

    ```java
    public class Main {
    
    	void test1() {}
    	
    	void test2() {}
    	
    	// etc...
    
    }
    ```


---

## 네이밍 원칙

### 클래스

1. UpperCamelCase 스타일로 작성한다

   예) `AuthServiceImpl`

2. 명사나 명사구로 작성하는 것을 지향한다. 필요할 경우 형용사나 형용사구로 작성해도 좋다.

### 메소드

1. lowelCamelCase 스타일로 작성한다.

   예) `saveUser`

2. 메서드 이름은 동사로 작성한다

### 상수

상수는 CONSTANT_CASE 스타일로 작성한다.

예) `MY_NUMBER`

### 필드 / 파라미터

1. 필드는 상수를 제외한 그 외에 필드들을 칭한다
2. lowelCamelCase 스타일로 작성한다.
3. `boolean` 변수에 경우 `is` 를 변수명 앞에 붙인다.
    1. `isChecked`

---

## 기타

### 오버라이딩

1. 항상 `@Override` 를 붙인다
2. 부모 메소드가 Deprecated 인 경우엔 생략한다.

### 그 외

그 외에 사항들은 따로 정의되지 않는 한 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)를 따르며 항상 가독성과 JAVA스러운 코드를 지향한다.

---

# :springboot: Spring Boot

## 기본 구조

프로젝트 폴더 구조는 아래와 같다. 예시를 위해 유저 도메인만 표시한다.

- 구조도 (길이로 인해 토글로 생략함)
    - domain
        - user
            - application
                - query
                    - impl
                - response
                - service
                    - impl
                - usecase
            - client
                - api
                - dto
                    - request
                    - response
            - domain
                - entity
                - repository
                    - jpa
                    - query
            - exception
                - error
    - global
        - global (…)

## 예외처리

기본적으로 예외처리는 모두 각 도메인에 맞게 커스텀한다.

예) UserNotFound / FileNotFound 처럼 같은 404라 해도 도메인 별로 커스텀하여 사용한다.

## ENUM

Enum 클래스에 들어가는 변수들은 모두 대문자로 작성하며 구분이 필요할 경우 ‘_’로 구분한다
예) USER_NOT_FOUND

## DTO

1. 모든 Request, DTO 들은 Record 클래스를 사용한다.
2. 상속처럼 일반 클래스가 필요할 경우 예외적으로 일반 클래스를 사용한다.

## Base Response

BaseResponse / BaseResponseData 클래스는 모두 컨트롤러 레이어에서만 사용되며 서비스 레이어에서의 사용을 금한다.

## Service Implement

1. 모든 서비스 레이어(+QueryDSL 관련 클래스)에 해당하는 클래스는 인터페이스로 추상화 하고 `Impl` 폴더 하단에 구현 클래스를 둔다.
2. 서비스 클래스는 기본적으로 하나의 Repository를 주입 받아 사용한다.
   예) UserService ← UserJpaRepository
    1. 서비스 로직은 하나의 클래스는 하나의 책임만 져야 하며 반드시 UseCase 클래스에서 해당 메서드들을 조합해 비즈니스 로직으로써 구현하도록 한다.

## 공통 클래스

전반적인 도메인에서 공통적으로 사용되는 클래스들은 `global` 폴더 하위에 있는 `common` 폴더 안에 정리한다.

## 명명 규칙

1. 모든 클래스들은 기본적으로 단어를 줄이지 않는다. 다만 아래에 해당하는 경우 예외로 둔다.
    1. Configuration → Config
    2. Implement → Impl
2. Controller 레이어에 해당하는 클래스들에 경우 [도메인명]ApiHandler라고 명명한다

## Controller

1. 컨트롤러 레이어에서는 그 어떠한 서비스 로직을 작성하지 않는다, 오로지 데이터를 전달하고 다시 반환하는 코드만 작성되어야 한다.
2. URL 및 Method 를 정하는 규칙은 Restful 하게 하는 것을 지향한다.

## Parameter

1. 메서드에 들어가는 파라미터에서 Request DTO 클래스가 들어갈 경우 파라미터 명은 request로 통일한다.
2. Entity 클래스가 파라미터로 들어갈 경우 파라미터 명은 entity로 통일한다
3. 만약 Requst DTO / Entity 클래스가 각각 2개 이상 파라미터로 주입될 경우 클래스 이름과 동일하게 파라미터 명을 지정한다.

### 어노테이션

1. 적용되는 클래스/메서드 상단에 작성하며 그 길이에 따라 가장 짧은 것을 상단, 가장 긴 것을 하단에 둔다.
2. 만약 그 길이가 동일할 경우 알파벳 순으로 정렬한다.

예)

```java
@Entity
@Getter
@SuperBuilder
@Table(name = "tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

	// 클래스 내용 (...)

}
```

## Base Entity 상속

1. 모든 Entity 클래스들은 특별한 사유가 없는 한 생성일과 수정일이 명시되어 있는 Base Entity를 상속받는다.
2. 해당 Entity와 동일한 정보를 담는 DTO 클래스 또한 생성일과 수정일을 명시한다.

---

# ⛔ ETC

## DB 테이블 명명

반드시 `tb` 를 테이블 이름 앞에 붙인다

예) tb_user

---

<aside>
⏫

[처음으로 가기](https://www.notion.so/2785b41f8384806ba83ac21522316958?pvs=21)

</aside>c