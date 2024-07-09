# Generate build
FROM maven:3.9.7-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY ../pom.xml /app/pom.xml
COPY patient-service/pom.xml /app/patient-service/pom.xml
COPY patient-service/src /app/patient-service/src
RUN mvn -f /app/patient-service/pom.xml clean package -DskipTests

# Dockerize
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/patient-service/target/patient-service-0.0.1-SNAPSHOT.jar ./patient-service-app.jar
EXPOSE 8081
CMD ["java", "-jar", "patient-service-app.jar"]
