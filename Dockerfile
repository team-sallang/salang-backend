# 멀티 스테이지 빌드를 사용하여 이미지 크기 최적화
FROM gradle:8.14.3-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 설정 파일들 복사
COPY build.gradle settings.gradle gradle.properties* ./

# 소스 코드 복사
COPY src ./src

# 애플리케이션 빌드 (테스트 스킵으로 빌드 시간 단축)
RUN gradle clean bootJar -x test --no-daemon

# 실행용 경량 이미지
FROM amazoncorretto:17-alpine

# 애플리케이션 사용자 생성 (보안)
RUN addgroup --system spring && adduser --system --ingroup spring spring

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 소유권 변경
RUN chown spring:spring app.jar

# 사용자 전환
USER spring

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]