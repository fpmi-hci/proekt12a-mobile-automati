package com.zlatamigas.readme.controller;

import com.zlatamigas.readme.controller.apimodel.request.LoginRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.request.OrderRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.request.RegisterRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.request.SearchParamsRequestAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookContentResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.BookResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.CartResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.GenreResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.LoginResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.RegisterResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.UserOrderResponseAPIModel;
import com.zlatamigas.readme.controller.apimodel.response.UserResponseAPIModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @POST("/auth/login")
    Call<LoginResponseAPIModel> login(@Body LoginRequestAPIModel loginRequestAPIModel);

    @POST("/auth/register")
    Call<RegisterResponseAPIModel> register(@Body RegisterRequestAPIModel registerRequestAPIModel);

    @GET("/books/{id}")
    Call<BookResponseAPIModel> getBookFullInfo(@Path("id") long id, @Header("Authorization") String authorization);

    @GET("/cart")
    Call<CartResponseAPIModel> getUserCart(@Header("Authorization") String authorization);

    @GET("/books/purchased")
    Call<List<BookResponseAPIModel>> getUserPurchased(@Header("Authorization") String authorization);

    @GET("/books")
    Call<List<BookResponseAPIModel>> getAllBooks();

    @POST("/books/search")
    Call<List<BookResponseAPIModel>> getSearchBooks(@Body SearchParamsRequestAPIModel requestAPIModel);

    @PUT("/cart/add/{bookId}")
    Call<CartResponseAPIModel> addToCart(@Path("bookId") long bookId, @Header("Authorization") String authorization);

    @PUT("/cart/remove/{bookId}")
    Call<CartResponseAPIModel> removeFromCart(@Path("bookId") long bookId, @Header("Authorization") String authorization);

    @POST("/orders")
    Call<UserOrderResponseAPIModel> addUserOrder(@Body OrderRequestAPIModel orderRequestAPIModel, @Header("Authorization") String authorization);

    @GET("/genres")
    Call<List<GenreResponseAPIModel>> getGenres();

    @GET("/books/{id}/content")
    Call<BookContentResponseAPIModel> getBookContent(@Path("id") long bookId, @Header("Authorization") String authorization);

    @GET("/users/self")
    Call<UserResponseAPIModel> getUserInfo(@Header("Authorization") String authorization);
}
