package com.zlatamigas.readme.controller.apimodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponseAPIModel {

    @SerializedName("blocked")
    @Expose
    private Boolean blocked;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private String role;

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}