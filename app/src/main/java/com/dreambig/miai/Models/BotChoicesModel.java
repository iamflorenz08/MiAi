package com.dreambig.miai.Models;

public class BotChoicesModel {
    private BotMessageModel message;

    public BotChoicesModel(BotMessageModel message) {
        this.message = message;
    }

    public BotMessageModel getMessage() {
        return message;
    }

    public void setMessage(BotMessageModel message) {
        this.message = message;
    }
}
