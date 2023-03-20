package com.dreambig.miai.Models;

import java.util.ArrayList;

public class BotResponseModel {
    private String id;
    private ArrayList<BotChoicesModel> choices;

    public BotResponseModel(String id, ArrayList<BotChoicesModel> choices) {
        this.id = id;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<BotChoicesModel> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<BotChoicesModel> choices) {
        this.choices = choices;
    }
}
