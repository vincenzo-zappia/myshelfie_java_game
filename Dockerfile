FROM openjdk:11

# Copy source folder
COPY src src

# Copy maven pom.xml
COPY pom.xml .

# Build the package
RUN mvn clean

RUN mvn install

# Expose the port 8080
EXPOSE 2023

# Run the code
ENTRYPOINT ["java","-jar","target/proj-ingsw-rj45-1.0-SNAPSHOT-jar-with-dependencies.jar", "--server"]
