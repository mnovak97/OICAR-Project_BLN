package com.example.oicar_project.Model;

import com.google.gson.annotations.SerializedName;

public class WorkType {

    @SerializedName("IdWorkType")
    private int idWorkType;

    @SerializedName("Title")
    private String title;

    @Override
    public String toString() {
        return this.title;
    }

    public WorkType() {
    }

    public WorkType(int idWorkType, String title) {
        this.idWorkType = idWorkType;
        this.title = title;
    }

    public int getIdWorkType() {
        return idWorkType;
    }

    public void setIdWorkType(int idWorkType) {
        this.idWorkType = idWorkType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
