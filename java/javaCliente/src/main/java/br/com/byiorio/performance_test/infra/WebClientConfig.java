package br.com.byiorio.performance_test.infra;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {
        private static final Integer SECONDS = 1;

        @Bean
        public WebClient httpClientLocalhost() {
                ConnectionProvider connProvider = ConnectionProvider.builder("webclient-conn-pool")
                                .maxConnections(10000)
                                .build();

                HttpClient httpClient = HttpClient
                                .create(connProvider)
                                .compress(true)
                                .responseTimeout(Duration.ofSeconds(SECONDS))
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, SECONDS * 1000)
                                .doOnConnected(c -> c.addHandlerLast(new ReadTimeoutHandler(SECONDS))
                                                .addHandlerLast(new WriteTimeoutHandler(SECONDS)));

                ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

                return WebClient.builder()
                                .clientConnector(connector)
                                .build();
        }
}
