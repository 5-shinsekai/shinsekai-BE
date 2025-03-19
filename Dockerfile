FROM openjdk:17-jdk-slim
COPY ./build/libs/*SNAPSHOT.jar shinsekai.jar
CMD ["java", "-jar", "shinsekai.jar"]
