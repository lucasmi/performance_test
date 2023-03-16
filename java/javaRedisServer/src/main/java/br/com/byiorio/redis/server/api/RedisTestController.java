package br.com.byiorio.redis.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/server/cpf")
public class RedisTestController {

	@Autowired
	RedisTestService service;

	@PostMapping(value = "/{cpf}", produces = "application/json")
	public ResponseEntity<Object> oferta(@PathVariable("cpf") String cpf, @RequestBody RedisDTO redisDTO) {

		service.insere(cpf, redisDTO);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
