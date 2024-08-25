
# Use tthe official Maven image to build the app
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /app/target/chat-*.jar /app/chat.jar

EXPOSE 8057

ENTRYPOINT ["java", "-jar", "/app/chat.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}"]