
# Use tthe official Maven image to build the app
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /app/target/discovery-*.jar /app/discovery.jar

EXPOSE 8761

CMD ["java", "-jar", "/app/discovery.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}"]