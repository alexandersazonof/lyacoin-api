package com.lyacoin.api.core.model;

public enum HistoryType {
    All("All"),
    Send("Send"),
    Receive("Receive");

    HistoryType(String value) {
        this.value = value;
    }

    public String value;
}
