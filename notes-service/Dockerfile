FROM eclipse-temurin:21-jre-alpine

COPY notes-service/target/notes-service-0.0.1-SNAPSHOT.jar ./notes-service-app.jar

EXPOSE 8082

CMD ["java", "-jar", "notes-service-app.jar"]
