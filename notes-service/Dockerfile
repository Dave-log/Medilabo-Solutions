# Generate build
FROM maven:3.9.7-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY ../pom.xml /app/pom.xml
COPY notes-service/pom.xml /app/notes-service/pom.xml
COPY notes-service/src /app/notes-service/src
RUN mvn -f /app/notes-service/pom.xml clean package -DskipTests

# Dockerize
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/notes-service/target/notes-service-0.0.1-SNAPSHOT.jar ./notes-service-app.jar
EXPOSE 8082
CMD ["java", "-jar", "notes-service-app.jar"]
