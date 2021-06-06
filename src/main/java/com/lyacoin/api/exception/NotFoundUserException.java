package com.lyacoin.api.exception;

public class NotFoundUserException extends AppException {

    public NotFoundUserException() {
        super(ExceptionMessageClient.INTERNAL_ERROR, "User not found");
    }
}
