# Use lightweight base image with Java 17
FROM openjdk:17-jdk-alpine

# Set working directory (optional but helpful)
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Expose the port your app runs on (usually 8080)
EXPOSE 8080

# Define the command to run your app
ENTRYPOINT ["java", "-jar", "app.jar"]