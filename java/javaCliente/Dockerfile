FROM openjdk:17
COPY ./target/*.jar /home/byiorio/client.jar
WORKDIR /home/byiorio
CMD java -Xms409m -Xmx409m -jar client.jar --server.port=8081
# CMD java -jar client.jar --server.port=8081
EXPOSE 8081