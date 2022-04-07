package br.com.byiorio.performance_test.teste1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class CircuitbreakerController {

    @Autowired
    PerformanceService performanceService;

    @GetMapping(path = "/circuit/v3/{accountNumber}/balance")
    public Mono<Object> webClient(@PathVariable(value = "accountNumber") Integer accountNumber) {
        return performanceService.usandoWebClientSemBlockCircuit(accountNumber);
    }

}