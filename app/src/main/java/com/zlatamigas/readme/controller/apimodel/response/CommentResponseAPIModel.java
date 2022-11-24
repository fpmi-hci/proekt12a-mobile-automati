package com.zlatamigas.readme.controller.apimodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentResponseAPIModel {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("user")
    @Expose
    private UserResponseAPIModel user;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public UserResponseAPIModel getUser() {
        return user;
    }

    public void setUser(UserResponseAPIModel user) {
        this.user = user;
    }

}