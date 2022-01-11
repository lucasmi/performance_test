package br.com.byiorio.performance_test.infra;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {
    @Bean
    public ClientHttpConnector getHttpClient() {

        ConnectionProvider connProvider = ConnectionProvider.builder("webclient-conn-pool")
                .maxConnections(4000)
                // .pendingAcquireTimeout(Duration.ofMillis(20000L))
                // .pendingAcquireMaxCount(2000)
                // // .maxIdleTime(Duration.ofMillis(1000L))
                // .maxLifeTime(Duration.ofMillis(1000L))
                .build();

        HttpClient httpClient = HttpClient
                .create(connProvider)
                // .compress(true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

        return new ReactorClientHttpConnector(httpClient);
    }
}
