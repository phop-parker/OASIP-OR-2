FROM openjdk:11-jdk
ARG JAR_FILE=/app/target/*.jar
EXPOSE --from=build ${JAR_FILE} or2.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","or2.jar"]