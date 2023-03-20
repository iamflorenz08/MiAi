package com.dreambig.miai.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dreambig.miai.Models.BotRequestModel;
import com.dreambig.miai.Models.BotResponseModel;
import com.dreambig.miai.Networks.OpenAiService;
import com.dreambig.miai.Utils.Env;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenAiRepo {
    private OpenAiService openAiService;
    private MutableLiveData<BotResponseModel> botResponse = new MutableLiveData<>();

    public OpenAiRepo(OpenAiService openAiService){
        this.openAiService = openAiService;
    }

    public void loadBotResponse(BotRequestModel request){
        openAiService.getBotResponse(Env.API_KEY, request).enqueue(new Callback<BotResponseModel>() {
            @Override
            public void onResponse(Call<BotResponseModel> call, Response<BotResponseModel> response) {
                if(response.isSuccessful()){
                    botResponse.postValue(response.body());
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

    public LiveData<BotResponseModel> getBotResponse() {
        return botResponse;
    }
}
