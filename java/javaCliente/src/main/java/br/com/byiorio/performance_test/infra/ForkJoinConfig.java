package br.com.byiorio.performance_test.infra;

import java.util.concurrent.ForkJoinPool;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ForkJoinConfig {

    @Value("${server.parallelism:200}")

    private Integer parallelism;

    @Bean
    public ForkJoinPool getForkJoinPool() {
        return new ForkJoinPool(parallelism);
    }

}