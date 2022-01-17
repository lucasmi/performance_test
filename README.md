## Pré requisitpos
Ter instalado
- Node  v16.13.1
- Apache Maven 3.8.4
- Java openjdk 17 2021-09-14
- Node autocannon v7.5.1

## Execução do java

- Entrar na pasta javaServer

-  Executar "mvn clean install"

-  Subir primeiro servidor "java -jar .\target\server-0.0.1-SNAPSHOT.jar --server.port=9090"

- Subir segundo servidor "java -jar .\target\server-0.0.1-SNAPSHOT.jar --server.port=9091"

-  Em outro terminal entrar na pasta javaCliente

- Executar "mvn clean install"

-  Executar "java -jar .\target\performance_test-0.0.1-SNAPSHOT.jar --server.port=8080"

## Execução do teste

## Teste sem pipeline
- Executar o comando "autocannon --debug -c 100 -a 1000 http://localhost:8081/v1/1/balance"

- Executar o comando "autocannon --debug -c 100 -a 1000 http://localhost:8081/v2/1/balance"

- Executar o comando "autocannon --debug -c 100 -a 1000 http://localhost:8081/v3/1/balance"

- Executar o comando "autocannon --debug -c 100 -a 1000 http://localhost:8081/v4/1/balance"

## Teste com 10 pipes
- Executar o comando "autocannon --debug -c 100 -a 1000 http://localhost:8081/v1/1/balance"

- Executar o comando "autocannon --debug -c 100 -p 10 -a 1000 http://localhost:8081/v2/1/balance"

- Executar o comando "autocannon --debug -c 100 -p 10 -a 1000 http://localhost:8081/v3/1/balance"

- Executar o comando "autocannon --debug -c 100 -p 10 -a 1000 http://localhost:8081/v4/1/balance"

## Execução do Node

- Entrar na pasta nodejs

- Executar o comando "npm install"

- Executar o comando "node cliente.js"

- Em outro terminal executar o comando "node server.js"

## Teste sem pipeline
- Executar o comando "autocannon --debug -c 100 -a 1000 http://localhost:8080/1/balance"

## Teste com 10 pipes
- Executar o comando "autocannon --debug -c 100 -p 10 -a 1000 http://localhost:8080/1/balance"