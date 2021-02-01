package com.example.oicar_project.Model;

import com.google.gson.annotations.SerializedName;

public class OfferModel {
    @SerializedName("IdOffer")
    private int offerId;

    @SerializedName("EmployeeIdUser")
    private int employeeId;

    @SerializedName("ListingIdListing")
    private int listingId;

    @SerializedName("Price")
    private double price;

    @SerializedName("HasTools")
    private boolean hasTools;

    public OfferModel() {
    }

    public OfferModel(int employeeId, int listingId, double price, boolean hasTools) {
        this.offerId = 0;
        this.employeeId = employeeId;
        this.listingId = listingId;
        this.price = price;
        this.hasTools = hasTools;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHasTools() {
        return hasTools;
    }

    public void setHasTools(boolean hasTools) {
        this.hasTools = hasTools;
    }
}
