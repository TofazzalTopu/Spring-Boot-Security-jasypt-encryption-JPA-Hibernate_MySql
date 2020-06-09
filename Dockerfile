FROM openjdk:8
EXPOSE 8080
ADD target/prescription-app.jar prescription-app.jar
ENTRYPOINT ["java", "-jar", "/prescription-app.jar"]