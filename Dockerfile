# Dockerfile for Spring Boot demo application
FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} demo-graphql.jar
ENTRYPOINT ["java", "-jar", "/demo-graphql.jar"]