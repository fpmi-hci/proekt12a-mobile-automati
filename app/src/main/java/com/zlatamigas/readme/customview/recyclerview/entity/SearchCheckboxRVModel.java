package com.zlatamigas.readme.customview.recyclerview.entity;

import java.util.Objects;

public class SearchCheckboxRVModel {

    private long id;
    private String value;
    private boolean selected;

    public SearchCheckboxRVModel(long id, String value, boolean selected) {
        this.id = id;
        this.value = value;
        this.selected = selected;
    }
    public SearchCheckboxRVModel(long id, String value) {
        this.id = id;
        this.value = value;
        this.selected = false;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
