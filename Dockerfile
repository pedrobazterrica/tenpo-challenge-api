FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/challenge.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","challenge.jar"]