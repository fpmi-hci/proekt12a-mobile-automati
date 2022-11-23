package com.zlatamigas.readme.controller;

import com.zlatamigas.readme.controller.apimodel.request.LoginRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.request.RegisterRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.LoginResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.RegisterResponseAPIModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("/auth/login")
    Call<LoginResponseAPIModel> login(@Body LoginRequestAPIModel loginRequestAPIModel);
    @POST("/auth/register")
    Call<RegisterResponseAPIModel> register(@Body RegisterRequestAPIModel registerRequestAPIModel);
}
