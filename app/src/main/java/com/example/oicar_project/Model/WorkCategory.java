package com.example.oicar_project.Model;

import com.google.gson.annotations.SerializedName;

public class WorkCategory {

    @SerializedName("IdWorkCategory")
    private int idWorkCategory;

    @SerializedName("Title")
    private String title;

    @Override
    public String toString() {
        return this.title;
    }

    public WorkCategory() {
    }

    public WorkCategory(int idWorkCategory, String title) {
        this.idWorkCategory = idWorkCategory;
        this.title = title;
    }

    public int getIdWorkCategory() {
        return idWorkCategory;
    }

    public void setIdWorkCategory(int idWorkCategory) {
        this.idWorkCategory = idWorkCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
