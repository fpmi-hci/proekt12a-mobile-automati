package com.zlatamigas.readme.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

import java.util.ArrayList;
import java.util.Collection;

public class OrderController {

    private static OrderController instance;

    private ArrayList<BookCommonInfoRVModel> selectedBooks;

    private OrderController(){
        selectedBooks = new ArrayList<>();
    }

    public static OrderController getInstance() {

        if(instance == null){
            instance = new OrderController();
        }

        return instance;
    }

    public ArrayList<BookCommonInfoRVModel> getSelectedBooks() {
        return selectedBooks;
    }

    public BookCommonInfoRVModel get(int index) {
        return selectedBooks.get(index);
    }

    public boolean add(BookCommonInfoRVModel bookCommonInfoRVModel) {
        return selectedBooks.add(bookCommonInfoRVModel);
    }

    public boolean remove(@Nullable Object o) {
        return selectedBooks.remove(o);
    }

    public boolean replaceAll(@NonNull Collection<? extends BookCommonInfoRVModel> c) {
        return selectedBooks.addAll(c);
    }

    public void clear() {
        selectedBooks.clear();
    }
}
