

# Use tthe official Maven image to build the app
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /app/target/user-*.jar /app/user.jar

EXPOSE 8056

CMD ["java", "-jar", "/app/user.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}"]