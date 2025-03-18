# Use official OpenJDK 17 image as a base
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and the build files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle /app/

# Download the dependencies
RUN ./gradlew build --no-daemon

# Copy the whole project
COPY . /app

# Build the application
RUN ./gradlew build --no-daemon

# Expose port 8080
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "shinsekai.jar"]
