FROM maven:3.8.6-eclipse-temurin-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:11-jre
COPY --from=build /app/target/course-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]