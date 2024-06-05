FROM openjdk:17-alpine as runner
WORKDIR /app
COPY ./build/libs/*SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/app.jar"]