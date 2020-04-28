package com.example.oicar_project.UserModel;

public class User {
    int idUser;
    String firstName;
    String lastName;
    String mobilePhone;
    String eMail;
    String password;


    public User(String firstname, String lastname, String mobilephone,String email,String pass ) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.mobilePhone = mobilephone;
        this.eMail = email;
        this.password = pass;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
