package com.example.oicar_project.Model;


import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("IdUser")
    private int userID;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("PhoneNumber")
    private String mobilePhone;

    @SerializedName("Email")
    private String eMail;

    @SerializedName("PasswordSalt")
    private String passwordSalt;

    @SerializedName("PasswordHash")
    private String passwordHash;

    @SerializedName("Balance")
    private double balance;

    @SerializedName("IsEmployer")
    private boolean isEmployer;

    @SerializedName("IBAN")
    private String IBAN;

    @SerializedName("Grade")
    private int grade;


    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", eMail='" + eMail + '\'' +
                ", passwordSalt='" + passwordSalt + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", balance=" + balance +
                ", grade=" + grade +
                '}';
    }

    public User() {
    }

    public User(String firstname, String lastname, String mobilephone, String email, boolean isEmployer, String iban) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.mobilePhone = mobilephone;
        this.eMail = email;
        this.isEmployer = isEmployer;
        this.IBAN = iban;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public boolean isEmployer() {
        return isEmployer;
    }

    public void setEmployer(boolean employer) {
        isEmployer = employer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
