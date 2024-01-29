package com.encore.board.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

//ControllerAdvice와 ExceptionHandler를 통해 예외처리의 공통화
@ControllerAdvice
@Slf4j
public class CommonException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> entityNotFoundHandler(EntityNotFoundException e) {
        log.error("Handler EntityNotFoundException Message : " + e.getMessage());
        return this.errResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("Handler IllegalArgumentException Message : " + e.getMessage());
        return this.errResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<Map<String, Object>> errResponse(HttpStatus httpStatus, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(httpStatus.value()));
        body.put("status message", httpStatus.getReasonPhrase());
        body.put("error message", message);
        return new ResponseEntity<>(body, httpStatus);
    }

}
