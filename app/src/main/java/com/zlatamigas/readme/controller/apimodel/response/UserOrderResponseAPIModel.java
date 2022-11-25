package com.zlatamigas.readme.controller.apimodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserOrderResponseAPIModel {

    @SerializedName("books")
    @Expose
    private List<BookResponseAPIModel> books = null;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("user")
    @Expose
    private UserResponseAPIModel user;

    public List<BookResponseAPIModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponseAPIModel> books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponseAPIModel getUser() {
        return user;
    }

    public void setUser(UserResponseAPIModel user) {
        this.user = user;
    }
}
