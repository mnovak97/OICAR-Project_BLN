package com.example.oicar_project.Model;

import android.graphics.PointF;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("IdLocation")
    private int idLocation;

    @SerializedName("Title")
    private String title;

    @SerializedName("Coordinates")
    private PointF coordinates;

    public Location() {
    }

    public Location(int idLocation, String title, PointF coordinates) {
        this.idLocation = idLocation;
        this.title = title;
        this.coordinates = coordinates;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PointF getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(PointF coordinates) {
        this.coordinates = coordinates;
    }
}
