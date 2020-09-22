package com.lyacoin.api.exception;

public class AppException extends RuntimeException {

    private String messageForClient;
    private String logMessage;

    public AppException(String messageForClient, String logMessage) {
        this.messageForClient = messageForClient;
        this.logMessage = logMessage;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public String getMessageForClient() {
        return messageForClient;
    }
}
