# MOSH Backend (Earth Team #4)

2025 ProfitLab 해커톤 - 페스티벌 전용 모바일 오더·대기 관리 솔루션

## 프로젝트 개요

MOSH는 공연과 부스 이용이 동시에 일어나는 혼잡한 페스티벌 현장에서, 오프라인 줄 서기로 인한 '공연 관람 기회 박탈'과 '체력 소모' 문제를 해결하는 서비스입니다.

## 기술 스택

- **Language**: Java 17
- **Framework**: Spring Boot 4.0.1
- **Database**: MySQL 8.0, Redis
- **Security**: Spring Security, JWT
- **Build Tool**: Gradle 9.2.1
- **Deployment**: Docker, AWS EC2, GitHub Actions

## 주요 기능

### 유저 측 (MVP)

1. **인증/회원가입**
   - Mock 소셜 로그인 (Kakao, Naver)
   - 전화번호 인증 (Redis 기반 OTP)
   - JWT 기반 인증

2. **티켓 인증**
   - 예매번호 인증으로 리워드 지급 (3,000 캐시)

3. **축제/부스 탐색**
   - 축제 목록 조회
   - 타임테이블 조회
   - 부스 검색 (F&B, MD, POPUP, EVENT)

4. **주문/결제**
   - 메뉴 선택 및 주문 생성
   - Mock 결제 (토스페이먼츠 기준)
   - CASH 결제 (리워드 사용)

5. **리뷰**
   - 메뉴별 리뷰 작성/조회

6. **알람**
   - 공연 시작 알림 설정 (Redis 인메모리)

7. **AI 챗봇**
   - Solar LLM API 연동 (프롬프트 엔지니어링)

## 프로젝트 구조

```
src/main/java/com/example/mosh_be/
├── config/              # 설정 (Security, Redis, WebClient)
├── controller/          # REST API 컨트롤러
├── domain/
│   ├── entity/         # JPA 엔티티
│   └── enums/          # Enum 타입
├── dto/                # Request/Response DTO
├── exception/          # 예외 처리
├── repository/         # JPA Repository
├── security/           # JWT 인증/인가
├── service/            # 비즈니스 로직
└── util/               # 유틸리티 클래스
```

## API 명세

모든 엔드포인트는 `/api/v1` prefix를 사용합니다.

### 인증
- `POST /api/v1/auth/social/{provider}/login` - 소셜 로그인
- `POST /api/v1/auth/phone/verifications` - 전화번호 인증 코드 발송
- `POST /api/v1/auth/phone/verifications/confirm` - 인증 코드 확인
- `POST /api/v1/auth/phone/login` - 전화번호 로그인

### 유저
- `GET /api/v1/users/me` - 내 프로필 조회
- `PATCH /api/v1/users/me` - 프로필 수정
- `GET /api/v1/users/me/rewards` - 캐시 잔액 조회
- `GET /api/v1/users/me/reward-transactions` - 리워드 트랜잭션 목록

### 티켓
- `POST /api/v1/tickets/verify` - 티켓 인증

### 축제
- `GET /api/v1/festivals` - 축제 목록
- `GET /api/v1/festivals/{festivalId}` - 축제 상세
- `GET /api/v1/festivals/{festivalId}/timetable` - 타임테이블

### 부스 & 메뉴
- `GET /api/v1/booths` - 부스 목록
- `GET /api/v1/booths/{boothId}` - 부스 상세
- `GET /api/v1/booths/{boothId}/menus` - 부스별 메뉴
- `GET /api/v1/menus/{menuId}` - 메뉴 상세

### 리뷰
- `GET /api/v1/menus/{menuId}/reviews` - 메뉴 리뷰 목록
- `POST /api/v1/menus/{menuId}/reviews` - 리뷰 작성

### 주문
- `GET /api/v1/orders` - 내 주문 목록
- `POST /api/v1/orders` - 주문 생성
- `GET /api/v1/orders/{orderId}` - 주문 상세
- `POST /api/v1/orders/{orderId}/cancel` - 주문 취소

### 결제
- `POST /api/v1/payments` - 결제 생성
- `POST /api/v1/payments/{paymentId}/confirm` - 결제 승인
- `POST /api/v1/payments/{paymentId}/cancel` - 결제 취소

### 알람
- `GET /api/v1/alarms` - 알람 목록
- `POST /api/v1/alarms` - 알람 설정
- `DELETE /api/v1/alarms/{alarmId}` - 알람 삭제

### 챗봇
- `POST /api/v1/chatbot/messages` - 챗봇 질의

## 환경 설정

### application.yml 환경 변수

```yaml
DB_USERNAME: MySQL 사용자명
DB_PASSWORD: MySQL 비밀번호
REDIS_HOST: Redis 호스트 (기본: 43.201.18.138)
REDIS_PORT: Redis 포트 (기본: 6379)
JWT_SECRET: JWT 시크릿 키
SOLAR_API_URL: Solar LLM API URL
SOLAR_API_KEY: Solar API 키
CORS_ALLOWED_ORIGINS: 허용할 CORS 도메인 (쉼표로 구분)
```

## 빌드 및 실행

### 로컬 빌드
```bash
./gradlew clean build
```

### Docker 빌드
```bash
docker build -t mosh-be:latest .
```

### Docker Compose 실행
```bash
docker-compose up -d
```

## CI/CD

- **ci.yml**: dev/feature 브랜치 PR 시 테스트 실행
- **deploy.yml**: main 브랜치 push 시 EC2 배포
