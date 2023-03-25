package com.dreambig.miai.Views.ChatFragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dreambig.miai.Models.BotMessageModel;
import com.dreambig.miai.Models.BotRequestModel;
import com.dreambig.miai.Models.BotResponseModel;
import com.dreambig.miai.Models.ChatModel;
import com.dreambig.miai.Repositories.OpenAiRepo;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatViewModel extends ViewModel {
    private OpenAiRepo openAiRepo;
    private MutableLiveData<ArrayList<BotMessageModel>> botResponse;
    private MutableLiveData<Boolean> isTyping;

    @Inject
    public ChatViewModel(OpenAiRepo openAiRepo){
        this.openAiRepo = openAiRepo;
    }

    public void init(String welcomeText){
        if(botResponse == null){
            ArrayList<BotMessageModel> messages = new ArrayList<>();
            messages.add(new BotMessageModel("assistant", welcomeText));
            botResponse = openAiRepo.getBotResponse();
            botResponse.setValue(messages);
        }

        if(isTyping == null){
            isTyping = openAiRepo.getIsTyping();
        }
    }

    public void askBot(String question){
        ArrayList<BotMessageModel> messages = botResponse.getValue();
        messages.add(new BotMessageModel("user", question));
        botResponse.setValue(messages);
        isTyping.setValue(true);
        openAiRepo.loadBotResponse(new BotRequestModel("gpt-3.5-turbo", botResponse.getValue()));
    }

    public LiveData<ArrayList<BotMessageModel>> getBotResponse() {
        return botResponse;
    }

    public LiveData<Boolean> getIsTyping() {
        return isTyping;
    }
}