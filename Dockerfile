FROM azul/zulu-openjdk:17-latest as builder

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

RUN mkdir /usr/src/app && mv /app.jar /usr/src/app/
WORKDIR /usr/src/app

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


FROM nginx:latest

COPY --from=builder /usr/src/app/app.jar /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]