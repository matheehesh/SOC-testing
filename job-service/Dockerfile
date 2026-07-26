# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -DskipTests package -Dhttps.protocols=TLSv1.2 -Dmaven.wagon.http.ssl.insecure=false

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
