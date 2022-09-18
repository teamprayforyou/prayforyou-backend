FROM openjdk:17-oracle
CMD ["./gradlew", "clean", "bootJar"]
ARG JAR_FILE_PATH=build/libs/backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
