package com.zlatamigas.readme.customview.recyclerview.mybooks;

import android.view.View;

import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

public interface BookMyBooksRVOptionsListener {
    void onBookClicked(BookCommonInfoRVModel book, View v);
    void onDownloadClicked(BookCommonInfoRVModel book, View v);
}
