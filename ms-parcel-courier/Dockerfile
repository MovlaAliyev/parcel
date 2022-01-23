FROM openjdk:17-oracle

LABEL maintainer="movla"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/ms-parcel-courier.jar

ADD ${JAR_FILE} ms-parcel-courier.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ms-parcel-courier.jar"]