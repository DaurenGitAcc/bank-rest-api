FROM openjdk:18-jdk-alpine3.14
WORKDIR /src
COPY /build/service.jar /src
EXPOSE 8080
CMD ["java","service.jar"]
