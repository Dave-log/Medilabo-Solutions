FROM eclipse-temurin:21-jre-alpine
COPY gateway-service/target/gateway-service-0.0.1-SNAPSHOT.jar ./gateway-service-app.jar
EXPOSE 8080
CMD ["java", "-jar", "gateway-service-app.jar"]