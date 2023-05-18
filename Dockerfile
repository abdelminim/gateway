FROM  openjdk:8u191-jre-alpine
RUN  echo "Africa/Cairo" > /etc/timezone
ARG JAR_FILE=target/MossGateway-1.0.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]