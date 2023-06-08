FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build --build-arg JAR_FILE=build/libs/*.jar -t app .
#docker run -p 8080:8080 app
