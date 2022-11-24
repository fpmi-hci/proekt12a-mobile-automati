package com.zlatamigas.readme.controller.apimodel.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchParamsRequestAPIModel implements Serializable {

    @SerializedName("pageNumber")
    @Expose
    private int pageNumber = 0;

    @SerializedName("pageSize")
    @Expose
    private int pageSize = 1000;

    @SerializedName("searchString")
    @Expose
    private String searchString;

    @SerializedName("searchType")
    @Expose
    private SearchType searchType;

    @SerializedName("sortDirection")
    @Expose
    private SortDirection sortDirection = SortDirection.ASC;

    public enum SearchType {
        TITLE_OR_AUTHOR,
        TITLE,
        AUTHOR
    }

    public enum SortDirection {
        ASC(0),
        DESC(1);

        private int id;

        SortDirection(int id) {
            this.id = id;
        }

        public static SortDirection getById(int id){
            if(id == 1){
                return DESC;
            } else {
                return ASC;
            }
        }
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }
}
