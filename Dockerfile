FROM openjdk:17

COPY backend/target/backend-0.0.1-SNAPSHOT.jar app.jar
COPY backend/target/classes/static/serbian-latin.txt serbian-latin.txt

ENTRYPOINT ["java", "-jar", "/app.jar"]