FROM openjdk:8u181
ARG jar_file
ENV JAR_FILE $jar_file
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]