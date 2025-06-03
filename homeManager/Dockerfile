# ======== Etapa 1: compilación con Maven =========
# Usamos el tag válido: maven:3.8.8-eclipse-temurin-17
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos solo pom.xml, mvnw y .mvn para bajar dependencias primero
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN mvn dependency:go-offline -B

# Copiamos el resto del código y construimos el JAR
COPY src src
RUN mvn package -DskipTests -B

# ======== Etapa 2: runtime con JRE =========
FROM eclipse-temurin:17-jre-jammy

ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
