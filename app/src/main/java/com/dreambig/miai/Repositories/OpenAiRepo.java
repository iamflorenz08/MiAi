package com.dreambig.miai.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dreambig.miai.Models.BotMessageModel;
import com.dreambig.miai.Models.BotRequestModel;
import com.dreambig.miai.Models.BotResponseModel;
import com.dreambig.miai.Networks.OpenAiService;
import com.dreambig.miai.Utils.Env;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenAiRepo {
    private OpenAiService openAiService;
    private MutableLiveData<ArrayList<BotMessageModel>> botResponse = new MutableLiveData<>();

    public OpenAiRepo(OpenAiService openAiService){
        this.openAiService = openAiService;
    }

    public void loadBotResponse(BotRequestModel request){
        openAiService.getBotResponse(Env.API_KEY, request).enqueue(new Callback<BotResponseModel>() {
            @Override
            public void onResponse(Call<BotResponseModel> call, Response<BotResponseModel> response) {
                if(response.isSuccessful()){
                    ArrayList<BotMessageModel> messages = new ArrayList<>(botResponse.getValue());
                    messages.add(response.body().getChoices().get(0).getMessage());
                    botResponse.postValue(messages);
                    return;
                }

                loadBotResponse(request);
                return;
            }

            @Override
            public void onFailure(Call<BotResponseModel> call, Throwable t) {
                loadBotResponse(request);
                return;
            }
        });
    }

    public MutableLiveData<ArrayList<BotMessageModel>> getBotResponse() {
        return botResponse;
    }
}
