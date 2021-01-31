package com.example.oicar_project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.oicar_project.MainActivity;


public class PreferenceUtils {

    public PreferenceUtils() {
    }

    public static void saveUserID(Integer id,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.USER,id);
        editor.commit();
    }
    public static Integer getUserID(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int userID = preferences.getInt(Constants.USER,0);
        return userID;
    }

    public static void saveUserEmail(String email,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USER_EMAIL,email);
        editor.commit();
    }

    public static String getUserEmail(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userEmail = preferences.getString(Constants.USER_EMAIL,null);
        return userEmail;
    }

    public static void clearPreference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void saveUserEmployer(boolean isEmployer, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.IS_EMPLOYER,isEmployer);
        editor.commit();
    }

    public static boolean getIsEmployer(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isEmployer = preferences.getBoolean(Constants.IS_EMPLOYER,true);
        return isEmployer;
    }
}
