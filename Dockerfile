FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

# 1) Copiamos únicamente pom.xml (y, opcionalmente, el wrapper de Maven)
#    para descargar dependencias sin copiar todo el código cada vez
COPY pom.xml mvnw ./
COPY .mvn .mvn

# 2) Pre-descarga de dependencias
RUN mvn dependency:go-offline -B

# 3) Copiamos el resto del código fuente y generamos el JAR (sin tests, para agilizar)
COPY src ./src
RUN mvn clean package -DskipTests -B

# ======== Etapa 2: runtime con JRE (runtime) =========
FROM eclipse-temurin:17-jre-jammy

# 4) Creamos un usuario no root para ejecutar la app
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup

WORKDIR /app

# 5) Copiamos el JAR generado (se asume que solo hay un *.jar en /app/target/)
COPY --from=build /app/target/*.jar /app/app.jar

# 6) Exponemos el puerto 8080 (informativo, el mapeo real se hace en docker-compose)
EXPOSE 8080

# 7) Forzamos a Spring a escuchar en 0.0.0.0 y habilitamos flush automático para que
#    todos los System.out.println lleguen sin buffering extra.
#    Usamos ENTRYPOINT en shell form para poder expandir JAVA_OPTS.
ENV JAVA_OPTS="-Dserver.address=0.0.0.0"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

# 8) Cambiamos a usuario no root para mayor seguridad
USER appuser
