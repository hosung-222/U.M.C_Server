# U.M.C_Server
UMC Matching Center Server (Spring Boot)

<br>

## 📄 프로젝트 소개
UMC(University MakeUs Challenge) 동아리 매칭 서비스 구축 프로젝트 백엔드입니다.

### 👥 맴버구성
<table>
 <tr>
    <td align="center"><a href="https://github.com/hosung-222"><img src="https://avatars.githubusercontent.com/hosung-222" width="140px;" alt=""></a></td>
    <td align="center"><a href="https://github.com/zzansol"><img src="https://avatars.githubusercontent.com/zzansol" width="140px;" alt=""></a></td>
    <td align="center"><a href="https://github.com/nahy-512"><img src="https://avatars.githubusercontent.com/nahy-512" width="140px;" alt=""></a></td>
    <td align="center"><a href="https://github.com/DongJun1110"><img src="https://avatars.githubusercontent.com/DongJun1110" width="140px;" alt=""></a></td>
  </tr>
  <tr>
    <td align="center"><a href="https://github.com/hosung-222"><b>캐슬/이호성</b></a></td>
    <td align="center"><a href="https://github.com/zzansol"><b>찬/김찬솔</b></a></td>
    <td align="center"><a href="https://github.com/nahy-512"><b>코코아/김나현</b></a></td>
    <td align="center"><a href="https://github.com/DongJun1110"><b>준/김동준</b></a></td>
  </tr>
</table>

<br>

## 🔨 개발 환경
- **JAVA 17**
- **Framework:** SpringBoot 2.X
- **DataBase:** MySQL

<br>

## 💬 Commit Message Convension

### 1. **Commit 메시지 구조**

> 기본 적인 커밋 메시지 구조는 제목,본문,꼬리말 세가지 파트로 나누고, 각 파트는 빈줄을 두어 구분한다.
> 

```java
type : subject

body

footer
```

### **2.  Commit Type**

> 타입은 태그와 제목으로 구성되고, 태그는 영어로 쓰되 첫 문자는 대문자로 한다.
> 

태그 : 제목의 형태이며, :뒤에만 space가 있음에 유의한다.

- `feat` : 새로운 기능 추가
- `fix` : 버그 수정
- `docs` : 문서 수정
- `style` : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- `refactor` : 코드 리펙토링
- `test` : 테스트 코드, 리펙토링 테스트 코드 추가
- `chore` : 빌드 업무 수정, 패키지 매니저 수정

### 3. Subject

> 제목은 최대 50글자가 넘지 않도록 하고 마침표 및 특수기호는 사용하지 않는다.
영문으로 표기하는 경우 동사(원형)를 가장 앞에 두고 첫 글자는 대문자로 표기한다.(과거 시제를 사용하지 않는다.)
제목은 개조식 구문으로 작성한다. --> 완전한 서술형 문장이 아니라, 간결하고 요점적인 서술을 의미.
> 

```java
- Fixed --> Fix
- Added --> Add
- Modified --> Modify
```

### 4. Body

> 본문은 다음의 규칙을 지킨다.
> 
- 본문은 한 줄 당 72자 내로 작성한다.
- 본문 내용은 양에 구애받지 않고 최대한 상세히 작성한다.
- 본문 내용은 어떻게 변경했는지 보다 무엇을 변경했는지 또는 왜 변경했는지를 설명한다.

### 5. footer

> 꼬릿말은 다음의 규칙을 지킨다.
> 
- 꼬리말은 optional이고 이슈 트래커 ID를 작성한다.
- 꼬리말은 "유형: #이슈 번호" 형식으로 사용한다.
- 여러 개의 이슈 번호를 적을 때는 쉼표(,)로 구분한다.
- 이슈 트래커 유형은 다음 중 하나를 사용한다.
    - `Fixes`: 이슈 수정중 (아직 해결되지 않은 경우)
    - `Resolves`: 이슈를 해결했을 때 사용
    - `Ref`: 참고할 이슈가 있을 때 사용
    - `Related to`: 해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)
    ex) Fixes: #45 Related to: #34, #23

### 6. 커밋예시

```java
feat: "회원 가입 기능 구현"

SMS, 이메일 중복확인 API 개발

Resolves: #123
Ref: #456
Related to: #48, #45

```

### 7. Commit Message Emogji

| 아이콘 | 코드 | 설명 |
| --- | --- | --- |
| 🎨 | `:art:` | 코드의 구조/형태 개선 |
| ⚡️ | `:zap: `| 성능 개선 |
| 🔥 | `:fire:` | 코드/파일 삭제 |
| 🐛 | `:bug: `| 버그 수정 |
| 🚑 | `:ambulance:` | 긴급 수정 |
| ✨ | `:sparkles:` | 새 기능 |
| 📝 | `:memo:` | 문서 추가/수정 |
| 🎉 | `:tada:` | 프로젝트 시작 |
| ✅ | `:white_check_mark:` | 테스트 추가/수정 |
| 🔒 | `:lock: `| 보안 이슈 수정 |
| 👷 | `:construction_worker:` | CI 빌드 시스템 추가/수정 |
| ♻️ | `:recycle: `| 코드 리팩토링 |
| ➕ | `:heavy_plus_sign:` | 의존성 추가 |
| ➖ |` :heavy_minus_sign:` | 의존성 제거 |
| 🔀 | `:twisted_rightwards_arrows:` | 브랜치 합병 |
| 🗃 | `:card_file_box:` | 데이버베이스 관련 수정 |
| 🙈 | `:see_no_evil:` | .gitignore 추가/수정 |
