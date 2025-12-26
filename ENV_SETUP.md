# 환경 변수 설정 가이드

## 1. .env 파일 생성

프로젝트 루트 디렉토리에 `.env` 파일을 생성하고 아래 내용을 참고하여 설정합니다.

```bash
# .env.example 파일을 복사하여 시작
cp .env.example .env
```

## 2. 환경 변수 설명

### Database Configuration

```bash
DB_USERNAME=mosh_user              # MySQL 사용자명
DB_PASSWORD=your_password          # MySQL 비밀번호 (변경 필수!)
DB_HOST=43.201.18.138             # MySQL 호스트 (EC2 IP)
DB_PORT=3306                       # MySQL 포트
DB_NAME=mosh                       # 데이터베이스 이름
```

**설정 방법:**
1. EC2 MySQL 컨테이너의 사용자명/비밀번호 확인
2. `docker-compose.yaml`의 MySQL 환경 변수와 일치시키기

### Redis Configuration

```bash
REDIS_HOST=43.201.18.138          # Redis 호스트 (EC2 IP)
REDIS_PORT=6379                    # Redis 포트
REDIS_PASSWORD=                    # Redis 비밀번호 (필요시 설정)
```

**설정 방법:**
1. Redis에 비밀번호를 설정했다면 입력
2. 설정하지 않았다면 비워두기

### JWT Configuration

```bash
JWT_SECRET=your-jwt-secret-key-minimum-64-characters-long
```

**설정 방법:**
1. 최소 64자 이상의 랜덤한 문자열 생성
2. 보안을 위해 복잡한 문자열 사용 권장

**생성 예시:**
```bash
# macOS/Linux
openssl rand -base64 64

# 또는
echo "mosh-jwt-secret-$(uuidgen)-$(date +%s)-$(openssl rand -hex 32)"
```

### Solar LLM API Configuration

```bash
SOLAR_API_URL=https://api.upstage.ai/v1/solar
SOLAR_API_KEY=your-solar-api-key-here
```

**설정 방법:**
1. 해커톤에서 제공받은 Upstage API 키 입력
2. URL은 기본값 유지

### CORS Configuration

```bash
CORS_ALLOWED_ORIGINS=http://localhost:3000,https://your-vercel-app.vercel.app
```

**설정 방법:**
1. 프론트엔드 로컬 개발 서버 주소 추가 (http://localhost:3000)
2. Vercel 배포 URL 추가 (FE 팀원에게 확인)
3. 여러 도메인은 쉼표(,)로 구분

**예시:**
```bash
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173,https://mosh-fe.vercel.app
```

### Server Configuration

```bash
SERVER_PORT=8080                   # 애플리케이션 서버 포트
SPRING_PROFILES_ACTIVE=dev         # Spring 프로필 (dev/prod)
```

## 3. EC2 환경 변수 설정

### GitHub Actions Secrets

EC2 배포를 위해 다음 Secrets를 GitHub Repository에 등록합니다:

```
Settings > Secrets and variables > Actions > New repository secret
```

필요한 Secrets:
- `EC2_HOST`: 43.201.18.138
- `EC2_USER`: ubuntu
- `EC2_SSH_KEY`: EC2 SSH 개인키 전체 내용
- `EC2_APP_DIR`: /home/ubuntu/MOSH-BE

### EC2 서버에 .env 파일 배포

```bash
# 로컬에서 EC2로 .env 파일 복사
scp .env ubuntu@43.201.18.138:/home/ubuntu/MOSH-BE/.env

# 또는 EC2에 직접 접속하여 생성
ssh ubuntu@43.201.18.138
cd /home/ubuntu/MOSH-BE
nano .env
# 내용 입력 후 저장
```

## 4. 환경별 설정

### 로컬 개발 환경

```bash
# .env
DB_HOST=43.201.18.138              # EC2 MySQL 사용
REDIS_HOST=43.201.18.138           # EC2 Redis 사용
CORS_ALLOWED_ORIGINS=http://localhost:3000
SPRING_PROFILES_ACTIVE=dev
```

### 프로덕션 환경 (EC2)

```bash
# .env
DB_HOST=mysql                      # docker-compose 서비스명
REDIS_HOST=redis                   # docker-compose 서비스명
CORS_ALLOWED_ORIGINS=https://your-vercel-app.vercel.app
SPRING_PROFILES_ACTIVE=prod
```

## 5. 환경 변수 검증

### 애플리케이션 시작 전 확인

```bash
# .env 파일 존재 확인
ls -la .env

# 환경 변수 로드 테스트 (선택사항)
source .env
echo $DB_USERNAME
echo $JWT_SECRET
```

### 애플리케이션 로그 확인

```bash
# 로컬 실행
./gradlew bootRun

# Docker 실행
docker-compose up

# 로그에서 다음 항목 확인:
# - Database connection 성공
# - Redis connection 성공
# - Server started on port 8080
```

## 6. 보안 주의사항

⚠️ **중요!**

1. `.env` 파일은 절대 Git에 커밋하지 않습니다
2. `.env.example`은 실제 값 없이 템플릿만 제공합니다
3. JWT_SECRET은 충분히 복잡하고 길게 설정합니다
4. 프로덕션 환경에서는 더 강력한 비밀번호를 사용합니다
5. API 키는 팀원들과 안전하게 공유합니다 (슬랙 DM, 보안 메신저 등)

## 7. 트러블슈팅

### DB 연결 실패

```
Error: Could not connect to database
```

**해결 방법:**
1. EC2 MySQL 컨테이너가 실행 중인지 확인
2. DB_USERNAME, DB_PASSWORD가 정확한지 확인
3. EC2 보안 그룹에서 3306 포트가 열려있는지 확인

### Redis 연결 실패

```
Error: Could not connect to Redis
```

**해결 방법:**
1. EC2 Redis 컨테이너가 실행 중인지 확인
2. REDIS_PASSWORD 설정 확인
3. EC2 보안 그룹에서 6379 포트가 열려있는지 확인

### CORS 에러

```
Access to XMLHttpRequest has been blocked by CORS policy
```

**해결 방법:**
1. CORS_ALLOWED_ORIGINS에 프론트엔드 URL이 정확히 입력되었는지 확인
2. http/https 프로토콜이 정확한지 확인
3. 포트 번호 확인

## 8. 팀 협업 가이드

### .env 파일 공유 방법

1. **절대 Git에 커밋하지 않기**
2. 팀원들에게 `.env.example` 파일 공유
3. 실제 값은 안전한 채널로 별도 공유 (슬랙 DM, 노션 등)
4. 각자 로컬에 `.env` 파일 생성

### 환경 변수 변경 시

1. `.env.example` 업데이트
2. 팀원들에게 변경 사항 공지
3. README.md 또는 이 문서 업데이트

---

문의사항이 있으면 백엔드 담당자에게 연락하세요!
