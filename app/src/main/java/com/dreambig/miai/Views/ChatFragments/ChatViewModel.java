package com.dreambig.miai.Views.ChatFragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dreambig.miai.Models.BotMessageModel;
import com.dreambig.miai.Models.BotRequestModel;
import com.dreambig.miai.Models.BotResponseModel;
import com.dreambig.miai.Repositories.OpenAiRepo;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatViewModel extends ViewModel {
    private OpenAiRepo openAiRepo;
    private LiveData<BotResponseModel> botResponse;
    @Inject
    public ChatViewModel(OpenAiRepo openAiRepo){
        this.openAiRepo = openAiRepo;
    }

    public void init(){
        if(botResponse == null){
            botResponse = openAiRepo.getBotResponse();
        }
    }

    public void askBot(String question){
        ArrayList<BotMessageModel> message = new ArrayList<>();
        message.add(new BotMessageModel("user",question));
        openAiRepo.loadBotResponse(new BotRequestModel("gpt-3.5-turbo", message));
    }

    public LiveData<BotResponseModel> getBotResponse() {
        return botResponse;
    }

}