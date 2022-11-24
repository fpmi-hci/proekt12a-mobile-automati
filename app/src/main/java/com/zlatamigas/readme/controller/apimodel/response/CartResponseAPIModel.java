package com.zlatamigas.readme.controller.apimodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponseAPIModel {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("books")
    @Expose
    private List<BookFullInfoResponseAPIModel> books = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<BookFullInfoResponseAPIModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookFullInfoResponseAPIModel> books) {
        this.books = books;
    }
}
