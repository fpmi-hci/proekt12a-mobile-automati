package com.zlatamigas.readme.customview.recyclerview.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class BookCommonInfoRVModel {

    private long id;
    private String title;
    private List<String> authors;
    private BigDecimal cost;

    public BookCommonInfoRVModel(long id, String title, List<String> authors, BigDecimal cost) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.cost = cost;
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

}
