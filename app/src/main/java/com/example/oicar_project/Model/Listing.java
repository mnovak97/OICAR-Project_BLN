package com.example.oicar_project.Model;

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

}
