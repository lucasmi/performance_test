package br.com.byiorio.server.teste1;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class ServerService {

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
