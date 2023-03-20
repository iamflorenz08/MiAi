package com.dreambig.miai.Networks;

import com.dreambig.miai.Models.BotRequestModel;
import com.dreambig.miai.Models.BotResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAiService {

    @Headers({"Content-Type: application/json"})
    @POST("/v1/chat/completions")
    Call<BotResponseModel> getBotResponse(@Header("Authorization") String api_key, @Body BotRequestModel request);


}
