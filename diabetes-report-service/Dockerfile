FROM eclipse-temurin:21-jre-alpine
COPY diabetes-report-service/target/diabetes-report-service-0.0.1-SNAPSHOT.jar ./diabetes-report-service-app.jar
EXPOSE 8083
CMD ["java", "-jar", "diabetes-report-service-app.jar"]
