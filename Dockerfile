FROM openjdk:17

COPY . .

ENTRYPOINT ["java", "-jar", "backend/target/backend-0.0.1-SNAPSHOT.jar"]