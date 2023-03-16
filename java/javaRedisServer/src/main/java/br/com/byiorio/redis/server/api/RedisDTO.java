package br.com.byiorio.redis.server.api;

import lombok.Data;

@Data
public class RedisDTO {
	private String nome;
	private Integer idade;
	private String descricao;
}
