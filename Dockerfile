FROM openjdk:17
WORKDIR /app
COPY ./build/libs/puddy-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
