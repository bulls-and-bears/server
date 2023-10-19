FROM azul/zulu-openjdk:17-latest as builder

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

RUN mkdir /usr/src/app && mv /app.jar /usr/src/app/
WORKDIR /usr/src/app

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
