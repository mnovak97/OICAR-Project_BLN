package com.example.oicar_project.Model;

import com.google.gson.annotations.SerializedName;

public class ReviewModel {
    @SerializedName("IdReview")
    private int idReview;

    @SerializedName("Grade")
    private int grade;

    @SerializedName("Comment")
    private String comment;

    @SerializedName("UserReviewerId")
    private int userReviewerId;

    @SerializedName("UserReviewedId")
    private int userReviewedId;

    public ReviewModel() {
    }

    public ReviewModel(int idReview, int grade, String comment, int userReviewerId, int userReviewedId) {
        this.idReview = idReview;
        this.grade = grade;
        this.comment = comment;
        this.userReviewerId = userReviewerId;
        this.userReviewedId = userReviewedId;
    }

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserReviewerId() {
        return userReviewerId;
    }

    public void setUserReviewerId(int userReviewerId) {
        this.userReviewerId = userReviewerId;
    }

    public int getUserReviewedId() {
        return userReviewedId;
    }

    public void setUserReviewedId(int userReviewedId) {
        this.userReviewedId = userReviewedId;
    }
}
