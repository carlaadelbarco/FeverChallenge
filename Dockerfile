FROM ubuntu:latest
LABEL authors="carlaalvarez"

ENTRYPOINT ["top", "-b"]

# Usa una imagen base con JDK 17
FROM openjdk:17-jdk-slim

# Configura el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de tu aplicación en el contenedor
COPY target/events-0.0.1-SNAPSHOT.jar /app/events-0.0.1-SNAPSHOT.jar
COPY wait-for-it.sh /app/wait-for-it.sh

# Comando para ejecutar tu aplicación
#CMD ["java", "-jar", "/app/events-0.0.1-SNAPSHOT.jar"]
CMD ["./wait-for-it.sh", "db:5432", "--", "java", "-jar", "/app/events-0.0.1-SNAPSHOT.jar"]
