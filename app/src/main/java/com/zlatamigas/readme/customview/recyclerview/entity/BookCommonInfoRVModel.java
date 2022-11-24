package com.zlatamigas.readme.customview.recyclerview.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookCommonInfoRVModel {

    private long id;
    private String title;
    private List<String> authors;
    private BigDecimal cost;
    private String imgUrl;

    public BookCommonInfoRVModel(){
        id = -1;
        title = "";
        authors = new ArrayList<>();
        cost = new BigDecimal(0);
        imgUrl = "";
    }

    public BookCommonInfoRVModel(long id, String title, List<String> authors, BigDecimal cost, String imgUrl) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.cost = cost;
        this.imgUrl = imgUrl;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
