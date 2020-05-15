package com.example.oicar_project.Repository;

import com.example.oicar_project.Model.User;

import java.util.List;

public class Repo {


    public static User getUserById(Long id) {
        List<User> users = User.getAllUsers();
        User requestedUser = new User();
        for (User user :users) {
            if (user.getId() == id){
                requestedUser = user;
            }
        }
        return requestedUser;
    }
}
