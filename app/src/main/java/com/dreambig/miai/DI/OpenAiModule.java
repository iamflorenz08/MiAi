package com.dreambig.miai.DI;

import com.dreambig.miai.Networks.OpenAiService;
import com.dreambig.miai.Repositories.OpenAiRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class OpenAiModule {

    @Provides
    @Singleton
    public OpenAiRepo getOpenAiRepoInstance(OpenAiService openAiService){
        return new OpenAiRepo(openAiService);
    }

    @Provides
    public OpenAiService getOpenAiServiceInstance(Retrofit retrofit){
        return retrofit.create(OpenAiService.class);
    }

    @Provides
    public Retrofit retrofitBuilder(){
        return new Retrofit.Builder()
                .baseUrl("https://api.openai.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
