FROM openjdk:17
COPY ./target/*.jar /home/byiorio/server.jar
WORKDIR /home/byiorio
CMD java -jar server.jar --server.port=9090
EXPOSE 9090