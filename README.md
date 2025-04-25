# shinsekai-BE

# ☕ Starbucks Store Clone Backend

> 스타벅스 앱스토어를 클론한 웹 쇼핑몰 백엔드 시스템입니다.  
> 실무 수준의 인증/인가, 장바구니 정책, 필터 시스템, 실시간 알림(SSE), 동적 배치 스케줄링을 구현했습니다.

<div align="center">
  <img src="https://github.com/user-attachments/assets/1194ccae-2cab-4eaa-9c71-5306b4be43e0" width="200" height="400"/>
  <img src="https://github.com/user-attachments/assets/69a5658e-45ec-4c8b-9afa-048f2f67e510" width="200" height="400"/>
</div>


## 📌 프로젝트 개요 (Overview)

- Java & Spring Boot 기반의 웹 백엔드 시스템
- API 중심 설계 및 도메인 기반 모듈화
- 실시간 알림, 배치 스케줄링, 필터 요약, 장바구니 정책 등 e-commerce의 핵심 로직 구현

## 🧱 시스템 아키텍처
![architecture](https://github.com/user-attachments/assets/e807a9dd-b8e1-4ab2-8f84-dc3e018265af)

> Spring Boot 기반으로 REST API 서버를 구축하고, Redis, MySQL, AWS 인프라, 배치 시스템 등을 연동한 구조입니다.



## 🗓️ 개발 기간
- 2025.03.13 ~ 2025.04.27 

## 🔧 기술 스택 (Tech Stack)

### 💻 Backend
- Java 17, Spring Boot 3.4.3
- Spring Security, OAuth 2.0, JWT
- JPA (Hibernate), QueryDSL
- Spring Batch
- Gradle

### 🗃 Database
- MySQL
- Redis (로그인/세션 관리, 캐싱)

### ☁️ Infra & DevOps
- Git, GitHub, GitHub Actions (CI/CD)
- AWS EC2, ECR, Route53, S3
- Docker, Docker Compose
- Nginx, SSL, Ubuntu, Bash Script

### 📦 주요 의존성 (Dependencies)

```gradle
// ✅ Spring Boot & Core
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springframework.boot:spring-boot-starter-batch'

// ✅ Security & 인증
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'
implementation 'io.jsonwebtoken:jjwt-api:0.12.5'
implementation 'io.jsonwebtoken:jjwt-impl:0.12.5'
implementation 'io.jsonwebtoken:jjwt-jackson:0.12.5'

// ✅ QueryDSL
implementation 'com.querydsl:querydsl-jpa:5.1.0:jakarta'
annotationProcessor 'com.querydsl:querydsl-apt:5.1.0:jakarta'

// ✅ AWS SDK (S3)
implementation platform("software.amazon.awssdk:bom:2.25.8")
implementation 'software.amazon.awssdk:s3'
implementation 'software.amazon.awssdk:auth'

// ✅ Swagger / API 문서화
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'

// ✅ Redis, Email, 기타
implementation 'org.springframework.boot:spring-boot-starter-data-redis'
implementation 'org.springframework.boot:spring-boot-starter-mail'
implementation 'org.mindrot:jbcrypt:0.4'

// ✅ DB / Lombok / 테스트
runtimeOnly 'com.mysql:mysql-connector-j'
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
```

## 📁 패키지 구조 (Package Structure)

```bash
com.example.shinsekai
├── batch
│   ├── bestProduct
│   │   ├── application      # 배치 Job/Step 구성 및 실행 로직
│   │   ├── domain           # 도메인 모델 (ex: BestProductEntity 등)
│   │   ├── dto.out          # 외부로 전달되는 응답 DTO
│   │   ├── infrastructure   # DB 연동 및 외부 API 호출
│   │   └── presentation     # 배치 상태 확인용 API (필요 시)
│   └── common
│       ├── BatchScheduler              # 정적 스케줄 실행 클래스
│       ├── DynamicBatchScheduler       # 동적 Cron 기반 스케줄러
│       ├── CronService, Repository ... # Cron 정보 저장/조회 처리
│
├── member
│   ├── application         # 로직 처리 서비스
│   ├── dto                 # 클라이언트 요청/응답 DTO
│   ├── vo                  # 클라이언트 요청/응답 VO
│   ├── entity              # 엔티티 정의
│   ├── infrastructure      # Repository 및 외부 연동
│   └── presentation        # Controller
│
├── common
│   ├── config              # Swagger, Security, Web 설정 등
│   ├── exception           # 예외 처리 및 에러 핸들링 구조
│   ├── jwt                 # JWT 발급/검증 관련 컴포넌트
│   ├── redis               # Redis 설정 및 인증토큰 캐싱
│   ├── entity              # 응답 공통 엔티티
│   └── presentation        # 공통 응답 포맷 등
│    
│
├── external.starbucksCard
│   ├── application         # 랜덤 카드 처리 서비스
│   ├── dto                 # 랜덤 금액/이미지 DTO
│   ├── presentation        # 외부 요청용 API
│   └── vo                  # View Object (랜덤 응답 포맷 구성)
│
├── sse
│   ├── application         # 알림 등록/구독 로직
│   └── presentation        # SSE 연결/구독 엔드포인트
│
├── util
│   ├── OneBasedPageable                # 1-based 페이징 어노테이션
│   └── OneBasedPageableResolver       # 해당 어노테이션을 처리하는 Resolver
└── ...
```
## 🗂️ 데이터베이스 ERD
![erd](https://github.com/user-attachments/assets/5c52ad55-8fab-4524-be40-47588e4bb6c2)

## ⚙️ 설치 및 실행 (Installation)
```bash
# 1. 프로젝트 클론
git clone https://github.com/your-id/starbucks-clone.git
cd starbucks-clone

# 2. 환경 변수 설정 (application.yml, application-dev.yml)
# 예시) application.yml
spring:
  profiles:
    active: dev

  jpa:
    hibernate:
      ddl-auto: update
      format_sql: true
    show-sql: true

    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true

  batch:
    job:
      enabled: false


# 예시) application-dev.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/shinsekai_db
    username: root
    password: your_pw
    driver-class-name: com.mysql.cj.jdbc.Driver

  data:
    redis:
      host: localhost
      port: 6379

  mail:
    host: smtp.gmail.com
    port: 587
    username: your_MAIL_USERNAME
    password: your_MAIL_PASSWORD
    properties:
      auth: true
      starttls: true
      sslTrust: smtp.gmail.com

  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: your_client_id
            redirect-uri: http://localhost:8080/api/v1/kakao/callback
            authorization-grant-type: authorization_code
            client-name: Kakao
            scope:
              - profile
              - account_email
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id

jwt:
  secret-key: your_secret_key
  token:
    access-expire-time: 1800000 # 30분
    refresh-expire-time: 1209600000  # 14일
client-url: localhost:3000


# 3. 실행
./gradlew bootRun
```


## 🚀 주요 기능 (Features)
### 🛒 장바구니 정책
- 상품 종류 최대 20개 제한
- 옵션 중복 포함 최대 수량 5개 제한
- 중복 방지 및 유효성 검사 포함

### 🎯 상품 필터링
- 카테고리 기반 필터: category, size, color, price, season
- 검색어 기반 필터: 상품명 검색

### 🕒 동적 배치 스케줄링
- DynamicBatchScheduler로 Cron 수정 가능
- 베스트 상품 자동 집계
- 한달 이상된 장바구니 항목 자동 삭제
- 재입고 알림 신청 내역 해당 유효 기간 경과 후 자동 삭제

### 🔔 실시간 알림 (SSE)
- 재입고 알림 서비스 제공
- 

### 🔐 인증/인가
- JWT 기반 사용자 인증
- OAuth2.0 기반 소셜 로그인


## 📘 API 문서 (Swagger UI)

- [Swagger UI 접속하기](http://localhost:8080/swagger-ui/index.html)



## 👩‍💻 개발자 정보 (Author)

|김정환|이영인|이수진|배부승|
|:---:|:---:|:---:|:---:|
|<a href="https://github.com/rlawjdghksdlqslek" target="_blank"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"></a>|<a href="https://github.com/LeeYeongin" target="_blank"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"></a>|<a href="https://github.com/Sujin31" target="_blank"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"></a>|<a href="https://github.com/bugling" target="_blank"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"></a>|



## 📚 참고자료 (References)
- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [JWT 공식 사이트](https://jwt.io/)
- [QueryDSL 문서](https://querydsl.com/)
- [JMeter 사용법](https://jmeter.apache.org/)
- [Docker Compose 공식 문서](https://docs.docker.com/compose/)
