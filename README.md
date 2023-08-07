QUARKUS BUILD + DOCKER:
./gradlew build -Dquarkus.package.type=native -Dquarkus.profile=docker
docker build -f src/main/docker/Dockerfile.native -t erp/service-discovery:1.0.1 .

SPRING + DOCKER:
docker build -f src/main/docker/Dockerfile -t erp/finances:1.0.1 .

FE + DOCKER:
docker build -f Dockerfile -t erp/fe:1.0.2 .

Every time bump up version

Next if using docker compose then:
docker compose up -d
