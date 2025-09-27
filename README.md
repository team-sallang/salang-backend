# Salang Backend

## 🚀 Quick Start

### Prerequisites
- Java 17
- Docker & Docker Compose

### Docker 실행 및 종료
- Makefile 참고

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot 3.4.9
- **Database**: PostgreSQL 
- **ORM**: Spring Data JPA, Hibernate  
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Gradle
- **Others**: Lombok, Validation

## 🌍 Environment

### Development
- 환경변수 설정이 필요 `.env` 파일 루트 디렉토리에 생성
- 추후 환경 변수가 추가된다면, .env파일에 추가한 뒤 팀원들에게 공유

### Test
- 테스트용 도커 컴포즈 파일을 활용해 db 서버를 띄운 다음 테스트 진행
  - Makefile을 통해 생성된 단축어 활용
- @SpringBootTest를 사용한다면 반드시 @ActiveProfiles("test")를 붙여주세요
