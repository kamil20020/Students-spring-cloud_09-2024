FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /home/app
COPY pom.xml .
COPY ./src ./src
RUN mvn package -DskipTests

FROM maven:3.8.5-openjdk-17
WORKDIR /home/app
COPY --from=build /home/app/target/config-0.0.1-SNAPSHOT.jar ./config-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "config-0.0.1-SNAPSHOT.jar"]