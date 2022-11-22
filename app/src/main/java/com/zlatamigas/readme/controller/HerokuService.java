package com.zlatamigas.readme.controller;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HerokuService {
    @GET("/book-controller/books")
    Call<ResponseBody> getAllBooks();
}
