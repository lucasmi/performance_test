package br.com.byiorio.redisclient.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.byiorio.redisclient.infra.PropertiesConfig;
import br.com.byiorio.redisclient.infra.WebClientConfig;
import reactor.core.publisher.Mono;

@Service
public class RedisTestService {

	@Autowired
	RedisRepository redisRepository;

	@Autowired
	WebClient.Builder webClientBuilder;

	@Autowired
	WebClientConfig webClientConfig;

	@Autowired
	PropertiesConfig config;

	public void insere(String cpf, RedisDTO redisDTO) {
		redisRepository.save(cpf, redisDTO);
	}

	public RedisDTO consulta(String cpf) {
		return redisRepository.findById(cpf);
	}

	public void insereViaHttp(String cpf, RedisDTO redisDTO) throws JsonProcessingException {
		String url = config.getRedisServerHttp().concat("/server/cpf/").concat(cpf);
		ObjectMapper object = new ObjectMapper();
		
		enviar(HttpMethod.POST, url, object.writeValueAsString(redisDTO), Void.class).block();
	}

	public <T> Mono<T> enviar(HttpMethod metodo, String url, String body, Class<T> responseType) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		webClientBuilder.clientConnector(webClientConfig.getHttpClient());

		return webClientBuilder
				.exchangeStrategies(ExchangeStrategies.builder()
						.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024)).build())
				.build()
				.method(metodo)
				.uri(builder.toUriString())
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(body), String.class)
				.retrieve()
				.bodyToMono(responseType);

	}
}
