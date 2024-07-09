FROM maven:3.9.7-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY ../pom.xml /app/pom.xml
COPY discovery-service/pom.xml /app/discovery-service/pom.xml
COPY discovery-service/src /app/discovery-service/src
RUN mvn -f /app/discovery-service/pom.xml clean package -DskipTests

# Dockerize
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/discovery-service/target/discovery-service-0.0.1-SNAPSHOT.jar ./discovery-service-app.jar
EXPOSE 8761
CMD ["java", "-jar", "discovery-service-app.jar"]