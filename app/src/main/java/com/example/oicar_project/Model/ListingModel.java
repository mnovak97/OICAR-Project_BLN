package com.example.oicar_project.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListingModel implements Serializable {
    @SerializedName("IdListing")
    private int idListing;

    @SerializedName("Title")
    private String title;

    @SerializedName("Description")
    private String description;

    @SerializedName("Longitude")
    private double longitude;

    @SerializedName("Latitude")
    private double latitude;

    @SerializedName("EmployerIdUser")
    private int employerId;

    @SerializedName("ToolsRequired")
    private boolean toolsRequired;

    @SerializedName("IsEmployeeReviewed")
    private boolean isEmployeeReviewed;

    @SerializedName("IsEmployerReviewed")
    private boolean isEmployerReviewed;

    @SerializedName("IsListed")
    private boolean isListed;

    @SerializedName("WorkTypeId")
    private int workTypeId;

    @SerializedName("WorkCategoryId")
    private int workCategoryId;

    @Override
    public String toString() {
        return "ListingModel{" +
                "idListing=" + idListing +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", employerId=" + employerId +
                ", toolsRequired=" + toolsRequired +
                ", IsEmployeeReviewed=" + isEmployeeReviewed +
                ", isEmployerReviewed=" + isEmployerReviewed +
                ", isListed=" + isListed +
                ", workTypeId=" + workTypeId +
                ", workCategoryId=" + workCategoryId +
                '}';
    }

    public ListingModel() {
    }

    public ListingModel(String title, String description, double latitude, double longitude, int employerId, boolean toolsRequired, int workTypeId, int workCategoryId, boolean isListed) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.employerId = employerId;
        this.toolsRequired = toolsRequired;
        this.workTypeId = workTypeId;
        this.workCategoryId = workCategoryId;
        this.isListed = isListed;
    }

    public boolean isEmployeeReviewed() {
        return isEmployeeReviewed;
    }

    public void setEmployeeReviewed(boolean employeeReviewed) {
        isEmployeeReviewed = employeeReviewed;
    }

    public boolean isEmployerReviewed() {
        return isEmployerReviewed;
    }

    public void setEmployerReviewed(boolean employerReviewed) {
        isEmployerReviewed = employerReviewed;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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

    public int getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(int workTypeId) {
        this.workTypeId = workTypeId;
    }

    public int getWorkCategoryId() {
        return workCategoryId;
    }

    public void setWorkCategoryId(int workCategoryId) {
        this.workCategoryId = workCategoryId;
    }

    public boolean isListed() {
        return isListed;
    }

    public void setListed(boolean listed) {
        isListed = listed;
    }
}
