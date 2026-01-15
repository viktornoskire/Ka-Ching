FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/backend-0.0.1-SNAPCHOT.jar"]