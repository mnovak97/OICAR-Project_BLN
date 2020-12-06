package com.example.oicar_project.Model;

import com.google.gson.annotations.SerializedName;

public class WorkType {

    @SerializedName("IdWorkType")
    private int idWorkCategory;

    @SerializedName("Title")
    private String title;

    public WorkType() {
    }

    public WorkType(int idWorkCategory, String title) {
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
