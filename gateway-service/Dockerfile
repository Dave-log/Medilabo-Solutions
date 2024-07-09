FROM maven:3.9.7-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY ../pom.xml /app/pom.xml
COPY gateway-service/pom.xml /app/gateway-service/pom.xml
COPY gateway-service/src /app/gateway-service/src
RUN mvn -f /app/gateway-service/pom.xml clean package -DskipTests

# Dockerize
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/gateway-service/target/gateway-service-0.0.1-SNAPSHOT.jar ./gateway-service-app.jar
EXPOSE 8080
CMD ["java", "-jar", "gateway-service-app.jar"]