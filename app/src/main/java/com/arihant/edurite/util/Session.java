package com.arihant.edurite.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.arihant.edurite.ui.activities.LoginActivity;

public class Session extends Object {

    private static final String TAG = "CustomLogs";
    private static final String PREF_NAME = "MY_BILLING_SOLUTIONS";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String Mobile = "mobile";
    private static final String Email = "email";
    private static final String UserId = "user_id";
    private static final String FCM = "FCM";
    private static final String User_name = "user_name";
    private static final String user_image = "user_image";

    private final Context _context;
    private final SharedPreferences SharedPref;
    private final SharedPreferences.Editor editor;

    public Session(Context context) {
        this._context = context;
        SharedPref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = SharedPref.edit();
        editor.apply();
    }

    public String getMobile() {
        return SharedPref.getString(Mobile, "");
    }

    public void setMobile(String mobile) {
        editor.putString(Mobile, mobile);
        editor.apply();
        editor.commit();
    }

    public String getFCM() {
        return SharedPref.getString(FCM, "");
    }

    public void setFCM(String s) {
        editor.putString(FCM, s);
        editor.apply();
        editor.commit();
    }

    public String getUserName() {
        return SharedPref.getString(User_name, "");

    }

    public void setUserName(String user_name) {
        editor.putString(User_name, user_name);
        this.editor.apply();
    }

    public String getUserId() {
        return SharedPref.getString(UserId, "");
    }

    public void setUserId(String userId) {
        editor.putString(UserId, userId);
        this.editor.apply();
    }

    public String getUserImage() {
        return SharedPref.getString(user_image, "");
    }

    public void setUserImage(String email) {
        editor.putString(user_image, email);
        editor.apply();
        editor.commit();
    }

    public String getEmail() {
        return SharedPref.getString(Email, "");
    }

    public void setEmail(String email) {
        editor.putString(Email, email);
        editor.apply();
        editor.commit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return SharedPref.getBoolean(IS_LOGGED_IN, false);
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(String key) {
        return SharedPref.getString(key, "");
    }

    public void logout() {
        editor.clear();
        editor.apply();
        Intent showLogin = new Intent(_context, LoginActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);
    }
}