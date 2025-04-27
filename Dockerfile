FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

#passa via argumento pois é info sensível
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY

#aqui pode ser var de ambiente mesmo
ENV AWS_REGION=us-east-1
ENV AWS_S3_BUCKET=qrcode-storage4452

#p/ executar o app.jar (é app.jar de certeza pois renomeei na linha 9)
ENTRYPOINT["java","-jar","app.jar"]