package br.com.byiorio.server.teste1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @Autowired
    ServerService cardService;

    @GetMapping("/{accountNumber}/card")
    public CardResponse card(@PathVariable(value = "accountNumber") String accountNumber) {
        return cardService.processCardNormal(accountNumber);
    }

    @GetMapping("/{accountNumber}/status")
    public StatusResponse status(@PathVariable(value = "accountNumber") String accountNumber) {
        return cardService.processStatusNormal(accountNumber);
    }

}
