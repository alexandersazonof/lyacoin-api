package com.lyacoin.api.rest;

import com.lyacoin.api.core.model.ErrorMessage;
import com.lyacoin.api.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerExceptionController {

    @ExceptionHandler(AppException.class)
    public ErrorMessage errorMessage(AppException e) {
        log.error(e.getLogMessage(), e);
        return message(e.getMessageForClient());
    }

    private ErrorMessage message(String message) {
        return ErrorMessage.builder().message(message).build();
    }
}
