package br.com.byiorio.redis.server.api;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Repository;

import br.com.byiorio.redis.server.infra.PropertiesConfig;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
	private final RedisOperations<String, RedisDTO> redisOperation;

	@Autowired
	PropertiesConfig config;

	public RedisDTO findById(String cpf) {
		return this.redisOperation.opsForValue().get("server:".concat(cpf));
	}

	public RedisDTO findAndDeleteById(String cpf) {
		return this.redisOperation.opsForValue().getAndDelete("server:".concat(cpf));
	}

	public void save(String cpf, RedisDTO redisDTO) {
		this.redisOperation.opsForValue().set("server:".concat(cpf), redisDTO,
				Duration.ofSeconds(config.getRedisExpiracao()));
	}

	public Boolean deleteById(String cpf) {
		return this.redisOperation.delete("server:".concat(cpf));
	}
}
