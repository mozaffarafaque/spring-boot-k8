FROM openjdk:11-jre-slim 
ARG JAR_FILE=build/libs/spring-boot-k8-1.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
