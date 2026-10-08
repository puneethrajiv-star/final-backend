FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Cache Maven dependencies layer
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/ai-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 10000

ENTRYPOINT ["sh", "-c", "exec java -Dserver.port=${PORT:-10000} -jar /app/app.jar"]
