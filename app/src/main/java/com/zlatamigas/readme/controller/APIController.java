package com.zlatamigas.readme.controller;

import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;
import com.zlatamigas.readme.customview.recyclerview.entity.SearchCheckboxRVModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class APIController {



    /////////////////////////////////////////////////////////////

    private ArrayList<BookCommonInfoRVModel> models;

    public APIController() {
        models = new ArrayList<>();

        models.add(new BookCommonInfoRVModel(1, "First book", Arrays.asList("Addam K."), new BigDecimal("12")));
        models.add(new BookCommonInfoRVModel(2, "Second book", Arrays.asList("Adew K."), new BigDecimal("2.3")));
        models.add(new BookCommonInfoRVModel(3, "Third book", Arrays.asList("Oddie M."), new BigDecimal("132")));
        models.add(new BookCommonInfoRVModel(4, "44444444444444 book", Arrays.asList("Tom S."), new BigDecimal("92")));
        models.add(new BookCommonInfoRVModel(5, "Last book book", Arrays.asList("Arma Lace"), new BigDecimal("34.7")));
    }

    public ArrayList<BookCommonInfoRVModel> getRandomBooksCommonInfo(){

        return new ArrayList<>(models);
    }

    public ArrayList<BookCommonInfoRVModel> getSearchBooksCommonInfo(){

        return new ArrayList<>(models);
    }

    public ArrayList<SearchCheckboxRVModel> getGenres(){
        ArrayList<SearchCheckboxRVModel> models = new ArrayList<>();

        models.add(new SearchCheckboxRVModel(1, "Детектив"));
        models.add(new SearchCheckboxRVModel(2, "Романтика"));
        models.add(new SearchCheckboxRVModel(3, "Фантастика"));
        models.add(new SearchCheckboxRVModel(4, "Мистика"));
        models.add(new SearchCheckboxRVModel(5, "Учебная литература"));
        models.add(new SearchCheckboxRVModel(1, "Детектив"));
        models.add(new SearchCheckboxRVModel(2, "Романтика"));
        models.add(new SearchCheckboxRVModel(3, "Фантастика"));
        models.add(new SearchCheckboxRVModel(4, "Мистика"));
        models.add(new SearchCheckboxRVModel(5, "Учебная литература"));
        models.add(new SearchCheckboxRVModel(1, "Детектив"));
        models.add(new SearchCheckboxRVModel(2, "Романтика"));
        models.add(new SearchCheckboxRVModel(3, "Фантастика"));
        models.add(new SearchCheckboxRVModel(4, "Мистика"));
        models.add(new SearchCheckboxRVModel(5, "Учебная литература"));

        return models;
    }

    public ArrayList<SearchCheckboxRVModel> getSearchTypes(){
        ArrayList<SearchCheckboxRVModel> models = new ArrayList<>();

        models.add(new SearchCheckboxRVModel(1, "по названию"));
        models.add(new SearchCheckboxRVModel(2, "по автору"));

        return models;
    }

    public ArrayList<BookCommonInfoRVModel> getUserCartBooks(long userId){

        return new ArrayList<>(models);
    }

//    public ArrayList<BookCommonInfoRVModel> getUserOrderBooks(long userId){
//
//        return new ArrayList<>(models);
//    }

    public ArrayList<BookCommonInfoRVModel> getUserBoughtBooks(long userId){

        return new ArrayList<>(models);
    }

    public boolean removeBookFromCart(long bookId, long userId){
        return true;
    }
}
