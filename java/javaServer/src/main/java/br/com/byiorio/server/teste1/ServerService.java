package br.com.byiorio.server.teste1;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class ServerService {

    public Mono<CardResponse> processCard(String accountNumber) {

        Scheduler singleThread = Schedulers.single();

        return Mono.just(accountNumber).publishOn(singleThread).map(updateTweet -> {
            CardResponse cardResponse = new CardResponse();
            try {

                if ("1".equals(accountNumber)) {
                    cardResponse.setCardNumber("1234-1234-1234-1234");
                } else {
                    cardResponse.setCardNumber("4567-4567-4567-4567");
                }

                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return cardResponse;
        });

    }

    public Mono<StatusResponse> processStatus(String accountNumber) {

        Scheduler singleThread = Schedulers.single();

        return Mono.just(accountNumber).publishOn(singleThread).map(updateTweet -> {
            StatusResponse statusResponse = new StatusResponse();
            try {

                if ("1".equals(accountNumber)) {
                    statusResponse.setCode("C01");
                    statusResponse.setDescription("Canceled");
                } else {
                    statusResponse.setCode("V99");
                    statusResponse.setDescription("Valid");
                }

                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return statusResponse;
        });

    }

    public CardResponse processCardNormal(String accountNumber) {

        CardResponse cardResponse = new CardResponse();
        try {

            if ("1".equals(accountNumber)) {
                cardResponse.setCardNumber("1234-1234-1234-1234");
            } else {
                cardResponse.setCardNumber("4567-4567-4567-4567");
            }

            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return cardResponse;

    }

    public StatusResponse processStatusNormal(String accountNumber) {
        StatusResponse statusResponse = new StatusResponse();
        try {

            if ("1".equals(accountNumber)) {
                statusResponse.setCode("C01");
                statusResponse.setDescription("Canceled");
            } else {
                statusResponse.setCode("V99");
                statusResponse.setDescription("Valid");
            }

            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return statusResponse;

    }

}
