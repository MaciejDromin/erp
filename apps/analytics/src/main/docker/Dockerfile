FROM openjdk:21-slim as runner
WORKDIR /app
COPY ./build/libs/*SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/app.jar"]