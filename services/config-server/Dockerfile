
# Use tthe official Maven image to build the app
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# Copy the JAR file
COPY --from=build /app/target/config-server-*.jar /app/config-server.jar

# Copy the configurations folder into the image
COPY src/main/resources/configurations /app/configurations

EXPOSE 8888

CMD ["java", "-jar", "/app/config-server.jar"]