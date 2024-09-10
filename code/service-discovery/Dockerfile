FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /home/app
COPY pom.xml .
COPY ./src ./src
RUN mvn -f pom.xml package

FROM maven:3.8.5-openjdk-17
WORKDIR /home/app
COPY --from=build /home/app/target/service-discovery-0.0.1-SNAPSHOT.jar ./service-discovery-0.0.1-SNAPSHOT.jar
EXPOSE 8100
ENTRYPOINT ["java", "-jar", "./service-discovery-0.0.1-SNAPSHOT.jar"]
