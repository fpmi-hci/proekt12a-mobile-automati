package com.zlatamigas.readme.customview.recyclerview.cart;

import android.view.View;

import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

public interface BookCartRVOptionsListener {
    void onBookClicked(BookCommonInfoRVModel book, View v);
    void onSelectCheckBoxClicked(BookCommonInfoRVModel book, View v);
    void onDeleteBookClicked(BookCommonInfoRVModel book, View v);
}
