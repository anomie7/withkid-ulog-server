FROM openjdk:8-jdk-slim-stretch
ENV SPRING_PROFILES_ACTIVE=production
VOLUME ["/tmp", "/usr/share/firebase"]
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} withkid-ulog-server.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/withkid-ulog-server.jar"]


