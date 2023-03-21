# Use a base image with Java installed
FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Copy the application jar file into the container
COPY target/darkstar-client-1.0.0-SNAPSHOT.jar /app

# Expose the port that the application listens on
EXPOSE 8090

# Run the application when the container starts
CMD ["java", "-jar", "darkstar-client-1.0.0-SNAPSHOT.jar"]