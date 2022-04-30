package br.com.byiorio.performance_test.teste1;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class PerformanceTestController {

    @Autowired
    PerformanceService performanceService;

    @GetMapping(path = "/v1/{accountNumber}/balance")
    public ResponseEntity<BalanceResponse> restTemplate(@PathVariable(value = "accountNumber") Integer accountNumber) {
        return ResponseEntity.ok(performanceService.usandoRestemplate(accountNumber));
    }

    @GetMapping(path = "/v2/{accountNumber}/balance")
    public ResponseEntity<BalanceResponse> webClientBlock(
            @PathVariable(value = "accountNumber") Integer accountNumber) {
        return ResponseEntity.ok(performanceService.usandoWebClientComBlock(accountNumber));
    }

    @GetMapping(path = "/v3/{accountNumber}/balance")
    public Mono<BalanceResponse> webClient(@PathVariable(value = "accountNumber") Integer accountNumber) {
        return performanceService.usandoWebClientSemBlock(accountNumber);
    }

    @GetMapping(path = "/v4/{accountNumber}/balance")
    public BalanceResponse feing(@PathVariable(value = "accountNumber") Integer accountNumber) {
        return performanceService.usandoFeign(accountNumber);
    }

    @GetMapping(path = "/v5/{accountNumber}/balance")
    public BalanceResponse feingAsync(@PathVariable(value = "accountNumber") Integer accountNumber)
            throws InterruptedException, ExecutionException {
        return performanceService.usandoFeignCompletableFuture(accountNumber);
    }
}