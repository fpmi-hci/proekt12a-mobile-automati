package com.zlatamigas.readme.customview.recyclerview.cart;

import android.view.View;

import com.zlatamigas.readme.customview.ItemBookCartView;
import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

public interface BookCartRVOptionsListener {
    void onBookClicked(BookCommonInfoRVModel book, ItemBookCartView v);
    void onSelectCheckBoxClicked(BookCommonInfoRVModel book, ItemBookCartView v);
    void onDeleteBookClicked(BookCommonInfoRVModel book, int position);
}
