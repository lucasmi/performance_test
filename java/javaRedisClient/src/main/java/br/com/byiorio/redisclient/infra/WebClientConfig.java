package br.com.byiorio.redisclient.infra;

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
				.maxConnections(2000)
				.pendingAcquireTimeout(Duration.ofMillis(1000))
				.build();

		HttpClient httpClient = HttpClient.create(connProvider).compress(true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

		return new ReactorClientHttpConnector(httpClient);
	}

}
