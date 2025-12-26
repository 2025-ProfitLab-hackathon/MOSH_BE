# ===== 1) Build stage =====
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Gradle wrapper & build files 먼저 복사 (캐시 효율)
COPY gradlew .
COPY gradle gradle
COPY build.gradle* settings.gradle* ./
RUN chmod +x gradlew

# 의존성 캐시용 (소스 없이도 가능한 단계)
RUN ./gradlew dependencies --no-daemon || true

# 실제 소스 복사 후 빌드
COPY src src
RUN ./gradlew clean bootJar -x test --no-daemon

# ===== 2) Run stage =====
FROM eclipse-temurin:17-jre
WORKDIR /app

# 보안/운영을 위해 non-root 유저 권장(옵션)
RUN useradd -m spring
USER spring

# 빌드 결과물 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# Spring Boot 기본 포트(너희는 8080)
EXPOSE 8080

# JAVA_TOOL_OPTIONS는 docker-compose에서 주입하므로 여기선 단순 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
