package br.com.byiorio.performance_test.teste1;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class PerformanceService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public WebClient httpClientLocalhost;

    public BalanceResponse restemplate(Integer accountNumber) {
        BalanceResponse balanceResponse = new BalanceResponse();

        // Primeiro endpoint
        ResponseEntity<CardResponse> responseCardEntity = restTemplate
                .getForEntity("http://localhost:9090/".concat(accountNumber.toString()).concat("/card"),
                        CardResponse.class);

        CardResponse cardResponse = responseCardEntity.getBody();
        if (cardResponse != null) {
            balanceResponse.setCardNumber(cardResponse.getCardNumber());
        }

        // Segundo endpoint
        ResponseEntity<StatusResponse> responseStatusEntity = restTemplate
                .getForEntity("http://localhost:9090/".concat(accountNumber.toString()).concat("/status"),
                        StatusResponse.class);

        StatusResponse statusResponse = responseStatusEntity.getBody();
        if (statusResponse != null) {
            balanceResponse.setStatus(statusResponse.getCode());
        }

        // ADiciona o blance
        BigDecimal balance;
        if (accountNumber == 1) {
            balance = new BigDecimal("300035");
        } else {
            balance = new BigDecimal("099");
        }
        balanceResponse.setBalance(balance.movePointLeft(2));

        return balanceResponse;
    }

    public Mono<BalanceResponse> webClientNonBlock(Integer accountNumber) {

        Mono<CardResponse> card = httpClientLocalhost
                .get()
                .uri("/".concat(accountNumber.toString()).concat("/card"))
                .retrieve()
                .bodyToMono(CardResponse.class);

        Mono<StatusResponse> status = httpClientLocalhost
                .get()
                .uri("/".concat(accountNumber.toString()).concat("/status"))
                .retrieve()
                .bodyToMono(StatusResponse.class);

        // Executa em paralelo
        return Mono.zip(card, status)
                .map(respostas -> {
                    BigDecimal balance;

                    if (accountNumber == 1) {
                        balance = new BigDecimal("300035");
                    } else {
                        balance = new BigDecimal("099");
                    }

                    return BalanceResponse.builder()
                            .status(respostas.getT2().getCode())
                            .cardNumber(respostas.getT1().getCardNumber())
                            .balance(balance.movePointLeft(2)).build();
                });
    }

    public BalanceResponse webClientBlock(Integer accountNumber) {
        BalanceResponse balanceResponse = new BalanceResponse();

        Mono<CardResponse> card = httpClientLocalhost
                .get()
                .uri("/".concat(accountNumber.toString()).concat("/card"))
                .retrieve()
                .bodyToMono(CardResponse.class);

        Mono<StatusResponse> status = httpClientLocalhost
                .get()
                .uri("/".concat(accountNumber.toString()).concat("/status"))
                .retrieve()
                .bodyToMono(StatusResponse.class);

        CardResponse cardResponse = card.block();
        StatusResponse statusResponse = status.block();

        if (cardResponse != null) {
            balanceResponse.setCardNumber(cardResponse.getCardNumber());
        }

        if (statusResponse != null) {
            balanceResponse.setStatus(statusResponse.getCode());
        }

        // ADiciona o blance
        BigDecimal balance;
        if (accountNumber == 1) {
            balance = new BigDecimal("300035");
        } else {
            balance = new BigDecimal("099");
        }
        balanceResponse.setBalance(balance.movePointLeft(2));

        return balanceResponse;
    }

}
