FROM eclipse-temurin:21-jre-alpine
COPY patient-service/target/patient-service-0.0.1-SNAPSHOT.jar ./patient-service-app.jar
COPY patient-service/mysql/init.sql /docker-entrypoint-initdb.d/
EXPOSE 8081
CMD ["java", "-jar", "patient-service-app.jar"]
