package com.example.oicar_project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.oicar_project.Model.User;
import com.google.gson.Gson;

public class PreferenceUtils {

    public PreferenceUtils() {
    }

    public static void saveUser(User user,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        editor.putString(Constants.USER,jsonUser);
        editor.commit();
    }
    public static User getUser(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonUser = preferences.getString(Constants.USER,null);
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser,User.class);
        return user;
    }

    public static void clearPreference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
