package br.com.byiorio.performance_test.teste1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "localServiceClient", url = "localhost:9090")
public interface FeignTesteClient {

    @GetMapping(value = "/{accountNumber}/card", produces = "application/json")
    CardResponse getCard(@PathVariable("accountNumber") Integer accountNumber);

    @GetMapping(value = "/{accountNumber}/status", produces = "application/json")
    StatusResponse getStatus(@PathVariable("accountNumber") Integer accountNumber);
}