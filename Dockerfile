FROM eclipse-temurin:22-jdk-alpine
WORKDIR /app
COPY target/moneyly-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java", "-jar", "myapp.jar"]