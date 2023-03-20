package com.dreambig.miai.Models;

import java.util.ArrayList;

public class BotRequestModel {
    private String model;
    private ArrayList<BotMessageModel> messages;

    public BotRequestModel(String model, ArrayList<BotMessageModel> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ArrayList<BotMessageModel> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<BotMessageModel> messages) {
        this.messages = messages;
    }
}
