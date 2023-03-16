package br.com.byiorio.redis.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisTestService {

	@Autowired
	RedisRepository redisRepository;

	public void insere(String cpf, RedisDTO redisDTO) {
		redisRepository.save(cpf, redisDTO);
	}

	public RedisDTO consulta(String cpf) {
		return redisRepository.findById(cpf);
	}
}
