package br.com.byiorio.performance_test.teste1;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceResponse {
    private String cardNumber;
    private BigDecimal balance;
    private String status;
}