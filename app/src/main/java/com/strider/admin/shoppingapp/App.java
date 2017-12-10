package com.strider.admin.shoppingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Admin on 12/8/2017.
 */

public class App {

    public static final String LOGIN = "login";
    public static final String SERVICE_URL = "http://vasudevkumaran.com/app/registration";

    public static void showToast(Context context ,String message)
    {
        Toast.makeText(context , message , Toast.LENGTH_LONG).show();
    }

    public static void setString(Context context , String key , String value)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(Context context , String key , String defaultValue)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key,defaultValue);
    }

    protected static void setLogin(Context context , String key , Boolean value)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    protected  static boolean isLoggedIn(Context context , String key)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
         return sharedPreferences.getBoolean(key , false);
    }

}
