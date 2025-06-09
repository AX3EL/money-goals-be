FROM maven:3.8.6-eclipse-temurin-17 as builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app
COPY --from=builder /app/target/moneyly-0.0.1-SNAPSHOT.jar myapp.jar

ENTRYPOINT ["java", "-jar", "myapp.jar"]