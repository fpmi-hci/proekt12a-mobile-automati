package com.zlatamigas.readme.controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIProvider {

    private static APIProvider instance;

    private final APIService service;

    private APIProvider(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://readmeapplication-env.eba-5kjirdez.us-east-1.elasticbeanstalk.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);
    }

    public static APIProvider getInstance() {

        if(instance == null){
            instance = new APIProvider();
        }

        return instance;
    }

    public APIService getService() {
        return service;
    }
}
