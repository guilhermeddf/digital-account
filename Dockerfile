FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080

COPY /build/libs/digital-account-0.0.1-SNAPSHOT.jar digital-account.jar

ENTRYPOINT ["java", "-jar", "digital-account.jar"]

