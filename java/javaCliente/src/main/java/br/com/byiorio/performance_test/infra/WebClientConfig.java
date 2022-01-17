package br.com.byiorio.performance_test.infra;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient httpClientLocalhost() {
        ConnectionProvider connProvider = ConnectionProvider.builder("webclient-conn-pool")
                .maxConnections(10000)
                // .pendingAcquireTimeout(Duration.ofMillis(20000L))
                // .pendingAcquireMaxCount(10000)
                // .maxIdleTime(Duration.ofMillis(5000L))
                // .maxLifeTime(Duration.ofMillis(5000L))
                .build();

        HttpClient httpClient = HttpClient
                .create(connProvider)
                // .create()
                // .compress(true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                // .baseUrl("http://localhost:9090")
                .clientConnector(connector)
                // .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
