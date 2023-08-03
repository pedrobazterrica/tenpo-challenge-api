FROM maven:3.8.6-openjdk-11-slim AS builder
WORKDIR /build
ADD . /build
RUN mvn clean install

FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=builder /build/target/challenge.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","challenge.jar"]