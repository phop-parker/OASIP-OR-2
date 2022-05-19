FROM maven:3.8.5-openjdk-11-slim as build
WORKDIR /app
COPY src/ ./src/
COPY pom.xml .
RUN rm -rf target
RUN mvn -D maven.test.skip package

FROM openjdk:11-jdk
ADD ./target/oasip-services-0.0.1-SNAPSHOT.jar or2.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","or2.jar"]

