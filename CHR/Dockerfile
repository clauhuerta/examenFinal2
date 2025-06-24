# Stage 1: Build with Maven
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el JAR desde la fase de build al WORKDIR /app
COPY --from=build /app/target/CHR-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8083

# Arrancamos el JAR local (no /app.jar, sino app.jar dentro de /app)
ENTRYPOINT ["java", "-jar", "app.jar"]
