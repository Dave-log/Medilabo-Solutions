FROM eclipse-temurin:21-jre-alpine
COPY discovery-service/target/discovery-service-0.0.1-SNAPSHOT.jar ./discovery-service-app.jar
EXPOSE 8761
CMD ["java", "-jar", "discovery-service-app.jar"]