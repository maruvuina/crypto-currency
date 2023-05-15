FROM openjdk:17-alpine
EXPOSE 8080
ARG JAR_FILE=build/libs/crypto-currency-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
