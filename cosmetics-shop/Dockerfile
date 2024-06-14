# Start with a base image containing Java runtime
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container at /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/cosmetic-shop.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "cosmetic-shop.jar"]
