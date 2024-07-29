# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/simple-java-app-1.0-SNAPSHOT.jar /app/simple-java-app-1.0-SNAPSHOT.jar

# Command to run the JAR file
CMD ["java", "-cp", "simple-java-app-1.0-SNAPSHOT.jar", "com.example.App"]
