FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080

COPY launcher/build/libs/launcher-0.0.1-SNAPSHOT.jar ital-account-app.jar

ENTRYPOINT ["java", "-jar", "digital-account-app.jar"]