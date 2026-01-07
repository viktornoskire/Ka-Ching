FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy the built Spring Boot jar
COPY target/*.jar app.jar

# Spring Boot default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]