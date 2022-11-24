package com.zlatamigas.readme.controller.apimodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorResponseAPIModel {

    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("id")
    @Expose
    private Long id;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
