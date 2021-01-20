package com.example.oicar_project.Model;

import android.graphics.PointF;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Listing {

    @SerializedName("IdListing")
    private int idListing;

    @SerializedName("Title")
    private String title;

    @SerializedName("Description")
    private String description;

    @SerializedName("Location")
    private Location location;

    @SerializedName("EmployerIdUser")
    private int employerId;

    @SerializedName("ToolsRequired")
    private boolean toolsRequired;

    @SerializedName("WorkType")
    private WorkType workType;

    @SerializedName("WorkCategories")
    private ArrayList<WorkCategory> WorkCategories;

    public Listing(String title, String description, int employerId, boolean toolsRequired, WorkType workType) {
        this.title = title;
        this.description = description;
        this.employerId = employerId;
        this.location = new Location("Gornji Bukovac 96",new PointF(45,22));
        this.toolsRequired = toolsRequired;
        this.workType = workType;
    }

    public int getIdListing() {
        return idListing;
    }

    public void setIdListing(int idListing) {
        this.idListing = idListing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public boolean isToolsRequired() {
        return toolsRequired;
    }

    public void setToolsRequired(boolean toolsRequired) {
        this.toolsRequired = toolsRequired;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public ArrayList<WorkCategory> getWorkCategories() {
        return WorkCategories;
    }

    public void setWorkCategories(ArrayList<WorkCategory> workCategories) {
        WorkCategories = workCategories;
    }
}
