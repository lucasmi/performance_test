package br.com.byiorio.redisclient.api;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Repository;

import br.com.byiorio.redisclient.infra.PropertiesConfig;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
	private final RedisOperations<String, RedisDTO> redisOperation;

	@Autowired
	PropertiesConfig config;

	public RedisDTO findById(String cpf) {
		return this.redisOperation.opsForValue().get("client:".concat(cpf));
	}

	public RedisDTO findAndDeleteById(String cpf) {
		return this.redisOperation.opsForValue().getAndDelete("client:".concat(cpf));
	}

	public void save(String cpf, RedisDTO redisDTO) {
		this.redisOperation.opsForValue().set("client:".concat(cpf), redisDTO,
				Duration.ofSeconds(config.getRedisExpiracao()));
	}

	public Boolean deleteById(String cpf) {
		return this.redisOperation.delete("client:".concat(cpf));
	}
}
