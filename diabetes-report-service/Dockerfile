# Generate build
FROM maven:3.9.7-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY ../pom.xml /app/pom.xml
COPY diabetes-report-service/pom.xml /app/diabetes-report-service/pom.xml
COPY diabetes-report-service/src /app/diabetes-report-service/src
RUN mvn -f /app/diabetes-report-service/pom.xml clean package -DskipTests

# Dockerize
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/diabetes-report-service/target/diabetes-report-service-0.0.1-SNAPSHOT.jar ./diabetes-report-service-app.jar
EXPOSE 8083
CMD ["java", "-jar", "diabetes-report-service-app.jar"]
