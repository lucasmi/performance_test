package br.com.byiorio.performance_test.infra;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", "122");
        body.put("errorMessage", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<Object> handleException(WebClientRequestException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", "122");
        body.put("errorMessage", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
