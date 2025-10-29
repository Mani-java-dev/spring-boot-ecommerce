FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/hammer-0.0.1-SNAPSHOT.jar /app/service.jar

EXPOSE 8080

CMD ["java","-jar","/app/service.jar"]

