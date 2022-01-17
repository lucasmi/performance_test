package br.com.byiorio.performance_test.teste1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "statusClient", url = "localhost:9091")
public interface FeignStatusClient {

    @GetMapping(value = "/{accountNumber}/status", produces = "application/json")
    StatusResponse getStatus(@PathVariable("accountNumber") Integer accountNumber);
}