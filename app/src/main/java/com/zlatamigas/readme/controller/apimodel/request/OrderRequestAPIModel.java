package com.zlatamigas.readme.controller.apimodel.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequestAPIModel {

    @SerializedName("books")
    @Expose
    private List<Long> books = null;

    public List<Long> getBooks() {
        return books;
    }

    public void setBooks(List<Long> books) {
        this.books = books;
    }


}
