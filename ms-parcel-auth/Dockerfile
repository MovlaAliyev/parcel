FROM openjdk:11-jre-slim-buster

LABEL maintainer="movla"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/ms-parcel-auth.jar

ADD ${JAR_FILE} ms-parcel-auth.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ms-parcel-auth.jar"]