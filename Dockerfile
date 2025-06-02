FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/parking-manager-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3003
ENTRYPOINT ["java", "-jar", "app.jar"]