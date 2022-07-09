# pick-movie

```Pick Movie (with Vue & SpringBoot)```

<br>

## 서비스 설명

올해의 최신 영화와 내가 좋아할만한 영화를 추천해주는 웹사이트

- 최소 기능 (MVP)
  - 태그를 이용해서 해당 영화가 무슨 영화 스타일인지 한눈에 볼 수 있게 설정
    - `액션` `호러` `SF` `로맨틱코미디` `공포` `애니`

  - 영화를 클릭하게 되면 내가 보고싶은 영화/드라마 다시보기 사이트랑 연결해준다 ( 해당 영화와 함께 )
    - ex) 넷플릭스, 왓챠, 웨이브 티빙, Youtube 

  - 로그인을 하였을 시에 내가 관심있는 영화/드라마를 추천해줌
  

- 추가 기능
  - 내가 자주 클릭하거나 관심 있어하는 영화 목록의 데이터를 저장해 추천 알고리즘을 만듦
  - 평점이나 댓글을 남길 수 있고 다른사람의 댓글에 재댓글을 달 수 있도록 멀티 스레드 형식으로 구현

<br>

## 공통 컨벤션 (Convention)

### 🌴 Branch 전략 
- Git Flow

|  **항목**  |                                  **설명**                                   |
| ---------- | --------------------------------------------------------------------------- |
| main       | 기준이 되는 브랜치로 제품을 배포하는 브랜치                                  |
| develop    | 개발 브랜치로 개발자들이 이 브랜치를 기준으로 각자 작업한 기능들을 Merge      |
| feature    | 단위 기능을 개발하는 브랜치로 기능 개발이 완료되면 develop 브랜치에 Merge     |
| release    | 배포를 위해 main 브랜치로 보내기 전에 먼저 QA(품질검사)를 하기위한 브랜치     |
| hotfix     | master 브랜치로 배포를 했는데 버그가 생겼을 떄 긴급 수정하는 브랜치           |

- Git Flow 과정
  - master 브랜치에서 develop 브랜치를 분기함
  - 개발자들은 develop 브랜치에 자유롭게 커밋을 함
  - 기능 구현이 있는 경우 develop 브랜치에서 feature/* 브랜치를 분기합니다.
  - 배포를 준비하기 위해 develop 브랜치에서 release/* 브랜치를 분기합니다.
  - 테스트를 진행하면서 발생하는 버그 수정은 release/* 브랜치에 직접 반영합니다.
  - 테스트가 완료되면 release 브랜치를 master와 develop에 merge함

- 보통 `main <- release <- develop(default) <- feature`
  - 모든 기능 구현은 `feature`을 이용해 브랜치를 분기해 개발 후 `devleop`으로 pull-request 하기!
  - 직접 `develop`으로 push ❌❌
- 예시 : `feature/frontend-build` 


### 🍕 Commit 전략 

|  **항목**  |             **설명**              |
| ---------- | ---------------------------------- |
| add        | (새로운) 기능 추가                 |
| refactor   | 기능 수정 or  코드 리팩토링        |
| style      | 코드 스타일링 추가                 |
| fix        | 버그 or 이슈 수정                  |
| test       | 테스트 코드 추가/수정              |
| docs       | 문서 수정                          |
| chore      | 빌드 업무 수정, 패키지 매니저 수정  |

- 타입은 태그와 제목으로 구성되고, 태그는 영어로 쓰되 첫 문자는 대문자로 함
- 태그 : 제목의 형태이며, :뒤에만 space가 있음에 유의함
- 예시 : `git commit -m "[add] : header feature implement"`
  - 추가 본문 메시지를 쓸 경우에도 메인 제목은 위와 같이 동일하게 작성


### 🍭 PR 전략
- PR Template에 따름

<br>

## Frontend

### ✨ 기술 설명
Vue, JavaScript, Vuex 를 이용해 Front-end 개발

### 🔥 기술 스택
> 사용 SPA 라이브러리 : Vue

|  **항목**  |  **기술 스택**  |
| ---------- | ---------------- |
| 환경세팅   | Vue CLI          |
| 사용언어   | JavaScript       |
| 스타일링   | CSS, SCSS        |
| 상태관리   | EventBus, Vuex   |
| 코드통합   | Prettier         |
| 에러검출   | Eslint           |
| API 통신   | Axios            |

### 🔅 코드 컨벤션
|  **항목**  |    **규칙**      |
| ---------- | ---------------- |
| Directory  | camelCase        |
| File       | PascalCase       |
| Constant   | SNAKE_CASE       |
| Variable   | camelCase        |
| Function   | camelCase        |


### ✔ 추가 라이브러리 & 버전 정보

|  **항목**  |  **버전 정보**  |
| ---------- | ---------------- |
| Node       | v12              |
| Vue        | v2.6.14          |
| Vuex       | v3.6.2           |
| Axios      | v0.27.2          |
| Sass       | v1.53.0          |


<br>

## Backend

### ✨ 기술 설명

### 🔥 기술 스택

### 🔅 코드 컨벤션

### ✔ 추가 라이브러리 & 버전 정보
