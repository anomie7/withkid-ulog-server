FROM openjdk:8-jdk-alpine
ENV SPRING_PROFILES_ACTIVE=production
VOLUME ["/tmp", "/usr/share"]
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} withkid-ulog-server.jar
RUN apk update
RUN apk add libc6-compat
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/withkid-ulog-server.jar"]


