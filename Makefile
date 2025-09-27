# Docker Dev 환경
docker-dev-up:
	docker-compose -f docker-compose.dev.yml up --build -d

docker-dev-down:
	docker-compose -f docker-compose.dev.yml down

docker-dev-down-v:
	docker-compose -f docker-compose.dev.yml down -v

# dev용 DB 컨테이너에서 salang DB 접속
docker-dev-db-salang:
	docker exec -it supabase-postgres psql -U postgres -d salang

# Docker Test 환경
docker-test-up:
	docker-compose -f docker-compose.test.yml up --build -d

docker-test-down:
	docker-compose -f docker-compose.test.yml down

docker-test-down-v:
	docker-compose -f docker-compose.test.yml down -v

# test용 DB 컨테이너에서 salang DB 접속
docker-test-db-salang:
	docker exec -it db-test psql -U postgres -d salang