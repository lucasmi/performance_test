package br.com.byiorio.performance_test.teste1;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;

@Service
public class PerformanceService {

    private static final String HTTP_LOCALHOST_9090 = "http://localhost:9090/";
    private static final String HTTP_LOCALHOST_9091 = "http://localhost:9091/";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient httpClientLocalhost;

    @Autowired
    FeignCardClient feignCardClient;

    @Autowired
    FeignStatusClient feignStatusClient;

    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    ForkJoinPool forkJoinPool;

    public BalanceResponse usandoRestemplate(Integer accountNumber) {
        BalanceResponse balanceResponse = new BalanceResponse();

        // Primeira Chamada
        ResponseEntity<CardResponse> responseCardEntity = restTemplate
                .getForEntity(HTTP_LOCALHOST_9090.concat(accountNumber.toString()).concat("/card"),
                        CardResponse.class);

        CardResponse cardResponse = responseCardEntity.getBody();

        if (cardResponse != null) {
            balanceResponse.setCardNumber(cardResponse.getCardNumber());
        }

        // Segunda chamada
        ResponseEntity<StatusResponse> responseStatusEntity = restTemplate
                .getForEntity(HTTP_LOCALHOST_9091.concat(accountNumber.toString()).concat("/status"),
                        StatusResponse.class);

        StatusResponse statusResponse = responseStatusEntity.getBody();
        if (statusResponse != null) {
            balanceResponse.setStatus(statusResponse.getCode());
        }

        // Adiciona o balance
        balanceResponse.setBalance(getBalance(accountNumber));

        return balanceResponse;
    }

    public BalanceResponse usandoWebClientComBlock(Integer accountNumber) {
        BalanceResponse balanceResponse = new BalanceResponse();

        // Primeira Chamada
        Mono<CardResponse> card = httpClientLocalhost
                .get()
                .uri(HTTP_LOCALHOST_9090.concat(accountNumber.toString()).concat("/card"))
                .retrieve()
                .bodyToMono(CardResponse.class);

        CardResponse cardResponse = card.block();

        if (cardResponse != null) {
            balanceResponse.setCardNumber(cardResponse.getCardNumber());
        }

        // Segunda Chamada
        Mono<StatusResponse> status = httpClientLocalhost
                .get()
                .uri(HTTP_LOCALHOST_9091.concat(accountNumber.toString()).concat("/status"))
                .retrieve()
                .bodyToMono(StatusResponse.class);

        StatusResponse statusResponse = status.block();

        if (statusResponse != null) {
            balanceResponse.setStatus(statusResponse.getCode());
        }

        // Adiciona o balance
        balanceResponse.setBalance(getBalance(accountNumber));

        return balanceResponse;
    }

    public Mono<BalanceResponse> usandoWebClientSemBlock(Integer accountNumber) {

        // Primeira Chamada
        Mono<CardResponse> card = httpClientLocalhost
                .get()
                .uri(HTTP_LOCALHOST_9090.concat(accountNumber.toString()).concat("/card"))
                .retrieve()
                .bodyToMono(CardResponse.class);

        // Segunda Chamada
        Mono<StatusResponse> status = httpClientLocalhost
                .get()
                .uri(HTTP_LOCALHOST_9091.concat(accountNumber.toString()).concat("/status"))
                .retrieve()
                .bodyToMono(StatusResponse.class);

        // Execução em paralelo
        return Mono.zip(card, status)
                .map(respostas -> {

                    return BalanceResponse.builder()
                            .status(respostas.getT2().getCode())
                            .cardNumber(respostas.getT1().getCardNumber())
                            .balance(this.getBalance(accountNumber))
                            .build();
                });
    }

    @CircuitBreaker(name = "externalServiceFoo")
    public Mono<Object> usandoWebClientSemBlockCircuit(Integer accountNumber) {

        // Primeira Chamada
        Mono<CardResponse> card = httpClientLocalhost
                .get()
                .uri(HTTP_LOCALHOST_9090.concat(accountNumber.toString()).concat("/card"))
                .retrieve()
                .bodyToMono(CardResponse.class);

        // Segunda Chamada
        Mono<StatusResponse> status = httpClientLocalhost
                .get()
                .uri(HTTP_LOCALHOST_9091.concat(accountNumber.toString()).concat("/status"))
                .retrieve()
                .bodyToMono(StatusResponse.class);

        // Execução em paralelo
        return Mono.zip(card, status)
                .map(respostas -> {

                    return BalanceResponse.builder()
                            .status(respostas.getT2().getCode())
                            .cardNumber(respostas.getT1().getCardNumber())
                            .balance(this.getBalance(accountNumber))
                            .build();
                });

    }

    public BalanceResponse usandoFeign(Integer accountNumber) {
        BalanceResponse balanceResponse = new BalanceResponse();

        // Primeira Chamada
        CardResponse cardResponse = feignCardClient.getCard(accountNumber);

        if (cardResponse != null) {
            balanceResponse.setCardNumber(cardResponse.getCardNumber());
        }

        // Segunda Chamada
        StatusResponse statusResponse = feignStatusClient.getStatus(accountNumber);

        if (statusResponse != null) {
            balanceResponse.setStatus(statusResponse.getCode());
        }

        // Adiciona o balance
        balanceResponse.setBalance(getBalance(accountNumber));

        return balanceResponse;
    }

    public BalanceResponse usandoFeignCompletableFuture(Integer accountNumber)
            throws InterruptedException, ExecutionException {
        BalanceResponse balanceResponse = new BalanceResponse();

        // Faz a primeira chamada
        CompletableFuture<CardResponse> cardinfo = CompletableFuture
                .supplyAsync(() -> feignCardClient.getCard(accountNumber), forkJoinPool);

        // Faz a segunda chamada
        CompletableFuture<StatusResponse> statusInfo = CompletableFuture
                .supplyAsync(() -> feignStatusClient.getStatus(accountNumber), forkJoinPool);

        // Juntando as respostas
        balanceResponse.setCardNumber(cardinfo.get().getCardNumber());
        balanceResponse.setStatus(statusInfo.get().getCode());

        // Adiciona o balance
        balanceResponse.setBalance(getBalance(accountNumber));

        return balanceResponse;
    }

    private BigDecimal getBalance(Integer accountNumber) {
        BigDecimal balance;
        if (accountNumber == 1) {
            balance = new BigDecimal("300035");
        } else {
            balance = new BigDecimal("099");
        }

        return balance.movePointLeft(2);
    }
}
