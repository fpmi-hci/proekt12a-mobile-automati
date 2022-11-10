package com.zlatamigas.readme.customview.recyclerview.search;

import android.view.View;

import com.zlatamigas.readme.customview.recyclerview.entity.BookCommonInfoRVModel;

public interface BookSearchRVOptionsListener {
    void onBookClicked(BookCommonInfoRVModel book, View v);
}
