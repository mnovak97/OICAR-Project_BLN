package com.example.oicar_project.UserModel;

import java.util.ArrayList;
import java.util.List;

public class TemporaryUserList {
    public static List<User> getUsers(){
        List<User> useri = new ArrayList<User>();
        useri.add(new User("Martin","Novak","0919158566","ipad3monkey@gmail.com","martin123"));
        useri.add(new User("Marko","Maric","0919158567","ipad4monkey@gmail.com","marko123"));

        return useri;
    }
}
