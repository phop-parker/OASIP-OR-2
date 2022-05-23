FROM maven:3.8.5-openjdk-11-slim as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn -D maven.test.skip package

FROM openjdk:11-jdk
ADD ./target/oasip-services-0.0.1-SNAPSHOT.jar or2.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","or2.jar"]

