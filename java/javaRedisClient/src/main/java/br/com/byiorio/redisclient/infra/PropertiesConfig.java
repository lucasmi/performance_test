package br.com.byiorio.redisclient.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@Repository
public class PropertiesConfig {

	@Value("${redis.ip}")
	private String redisIp;

	@Value("${redis.porta}")
	private int redisPorta;

	@Value("${redis.expiracao.segundos}")
	private int redisExpiracao;
	
	@Value("${redis.server.http}")
	private String redisServerHttp;
}
