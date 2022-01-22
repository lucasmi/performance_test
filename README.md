# Mostrando o funcionamento
https://www.byiorio.com.br/blog/1

## Pré requisitpos
Ter instalado
- Node  v16.13.1
- Apache Maven 3.8.4
- Java openjdk 17 2021-09-14
- Node autocannon v7.5.1
- Uso do Http 1.1

## Execução do java

1. Entrar na pasta javaServer

2. Executar "mvn clean install"

3. Subir primeiro servidor "java -jar .\target\server-0.0.1-SNAPSHOT.jar --server.port=9090"

4. Em outro terminal repetir passo 1 e subir o segundo servidor com o comando "java -jar .\target\server-0.0.1-SNAPSHOT.jar --server.port=9091"

5. Em outro terminal entrar na pasta javaCliente

6. Executar "mvn clean install"

7.  Executar "java -jar .\target\performance_test-0.0.1-SNAPSHOT.jar --server.port=8080"

## Execução do teste

## Teste sem pipeline
- Executar o comando "autocannon --debug -c 100 -a 10000 http://localhost:8081/v1/1/balance"

- Executar o comando "autocannon --debug -c 100 -a 10000 http://localhost:8081/v2/1/balance"

- Executar o comando "autocannon --debug -c 100 -a 10000 http://localhost:8081/v3/1/balance"

- Executar o comando "autocannon --debug -c 100 -a 10000 http://localhost:8081/v4/1/balance"

## Teste com 10 pipes
- Executar o comando "autocannon --debug -c 100 -a 1000 http://localhost:8081/v1/1/balance"

- Executar o comando "autocannon --debug -c 100 -p 10 -a 10000 http://localhost:8081/v2/1/balance"

- Executar o comando "autocannon --debug -c 100 -p 10 -a 10000 http://localhost:8081/v3/1/balance"

- Executar o comando "autocannon --debug -c 100 -p 10 -a 10000 http://localhost:8081/v4/1/balance"

## Execução do Node

1. Entrar na pasta nodejs

2. Executar o comando "npm install"

3. Executar o comando "node cliente.js"

4. Em outro terminal executar o comando "node server.js"

## Teste sem pipeline
- Executar o comando "autocannon --debug -c 100 -a 10000 http://localhost:8080/1/balance"

## Teste com 10 pipes
- Executar o comando "autocannon --debug -c 100 -p 10 -a 10000 http://localhost:8080/1/balance"
