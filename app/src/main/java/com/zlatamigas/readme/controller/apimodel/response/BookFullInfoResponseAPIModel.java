package com.zlatamigas.readme.controller.apimodel.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class BookFullInfoResponseAPIModel {

    @SerializedName("authors")
    @Expose
    private List<AuthorResponseAPIModel> authors = null;
    @SerializedName("bookFile")
    @Expose
    private String bookFile;
    @SerializedName("comments")
    @Expose
    private List<CommentResponseAPIModel> comments = null;
    @SerializedName("cost")
    @Expose
    private BigDecimal cost;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("genres")
    @Expose
    private List<GenreResponseAPIModel> genres = null;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("purchasedByUser")
    @Expose
    private boolean purchasedByUser;

    public List<AuthorResponseAPIModel> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorResponseAPIModel> authors) {
        this.authors = authors;
    }

    public String getBookFile() {
        return bookFile;
    }

    public void setBookFile(String bookFile) {
        this.bookFile = bookFile;
    }

    public List<CommentResponseAPIModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseAPIModel> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GenreResponseAPIModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreResponseAPIModel> genres) {
        this.genres = genres;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPurchasedByUser() {
        return purchasedByUser;
    }

    public void setPurchasedByUser(boolean purchasedByUser) {
        this.purchasedByUser = purchasedByUser;
    }
}



