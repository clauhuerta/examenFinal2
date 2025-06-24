# Stage 1: Build con Maven en JDK 17
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos POM y código fuente
COPY pom.xml .
COPY src ./src

# Empaquetamos (salteamos tests para ir más rápido)
RUN mvn clean package -DskipTests

# Stage 2: Runtime con JRE 17 Alpine
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiamos el JAR generado. Ajusta el patrón si tu artifactId o versión cambian.
COPY --from=build /app/target/*.jar app.jar

# El frontend está configurado para arrancar en el puerto 8084
EXPOSE 8084

# Arrancamos la aplicación
ENTRYPOINT ["java","-jar","app.jar"]
