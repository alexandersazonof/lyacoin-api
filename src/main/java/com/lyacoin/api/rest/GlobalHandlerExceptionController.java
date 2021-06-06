package com.lyacoin.api.rest;

import com.lyacoin.api.core.model.ErrorMessage;
import com.lyacoin.api.exception.AppException;
import com.lyacoin.api.exception.ExceptionMessageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerExceptionController {

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage errorMessage(AppException e) {
        log.error(e.getLogMessage(), e);
        return message(e.getMessageForClient());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage methodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("Incorrect fields: " + e);
        return message("Incorrect fields");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage exception(Exception e) {
        log.error("Exception: " + e);
        return message(ExceptionMessageClient.INTERNAL_ERROR);
    }

    private ErrorMessage message(String message) {
        return ErrorMessage.builder().message(message).build();
    }
}
