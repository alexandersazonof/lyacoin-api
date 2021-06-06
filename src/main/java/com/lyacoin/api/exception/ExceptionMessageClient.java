package com.lyacoin.api.exception;

public interface ExceptionMessageClient {
    String INTERNAL_ERROR = "Server error, please try again later";
    String INCORRECT_SEED = "Incorrect seed phrase";
    String CAN_NOT_CREATE_ACCOUNT = "Can not create account";
}
