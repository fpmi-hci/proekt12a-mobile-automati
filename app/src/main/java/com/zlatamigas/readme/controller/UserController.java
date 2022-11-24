package com.zlatamigas.readme.controller;

public class UserController {
    private static UserController instance;

    private String token;

    private UserController(){
        token = "";
    }

    public static UserController getInstance() {

        if(instance == null){
            instance = new UserController();
        }

        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
