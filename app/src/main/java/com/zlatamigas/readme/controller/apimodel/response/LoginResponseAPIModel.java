package com.zlatamigas.readme.controller.apimodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseAPIModel {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("token")
    @Expose
    private String token;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}