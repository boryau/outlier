FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8086
ARG JAR_FILE=target/outlier-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} outlier.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/outlier.jar"]