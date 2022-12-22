package com.zlatamigas.readme.controller;

public class CommonController {
    private static CommonController instance;


    private CommonController(){

    }

    public static CommonController getInstance() {

        if(instance == null){
            instance = new CommonController();
        }

        return instance;
    }

}
