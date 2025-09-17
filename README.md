# Salang Backend

## 🚀 Quick Start

### Prerequisites
- Java 17
- Docker & Docker Compose

### Docker 실행 및 종료
```bash
// 실행
docker-compose -f docker-compose.dev.yml up --build -d

// 종료(볼륨 삭제 X)
docker-compose -f docker-compose.dev.yml down

// 종료(볼륨도 함께 삭제)
docker-compose -f docker-compose.dev.yml down -v
```

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot 3.4.9
- **Database**: PostgreSQL (운영), H2 (테스트)
- **ORM**: Spring Data JPA, Hibernate  
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Gradle
- **Others**: Lombok, Validation

## 🌍 Environment

### Development
- 환경변수 설정이 필요 `.env` 파일 루트 디렉토리에 생성
- 추후 환경 변수가 추가된다면, .env파일에 추가한 뒤 팀원들에게 공유

### Test
- H2 인메모리 데이터베이스를 자동으로 사용
- @SpringBootTest를 사용한다면 @ActiveProfiles("test")를 붙여주세요