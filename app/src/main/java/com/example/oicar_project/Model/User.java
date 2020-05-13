package com.example.oicar_project.Model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name ="User")
public class User extends Model {

    @Column(name = "firstName")
    String firstName;

    @Column(name = "lastName")
    String lastName;

    @Column(name = "mobilePhone")
    String mobilePhone;

    @Column(name = "eMail")
    String eMail;

    @Column(name = "password")
    String password;

    public User(){

    }

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

    public static List<User> getAllUsers(){
        return new Select().from(User.class).execute();
    }
}
