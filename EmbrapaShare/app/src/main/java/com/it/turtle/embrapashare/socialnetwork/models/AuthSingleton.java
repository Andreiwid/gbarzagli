package com.it.turtle.embrapashare.socialnetwork.models;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class AuthSingleton {

    private static AuthSingleton instance;

    private static String name;
    private static Uri picture;

    private static GoogleSignInResult signInResult;

    private AuthSingleton () {
    }

    public static AuthSingleton getInstance () {
        checkInstance();
        return instance;
    }

    private static void checkInstance () {
        if (instance != null) {
            instance = new AuthSingleton();
        }
    }

    public static String getName() {
        checkInstance();
        return name;
    }

    public static void setName(String name) {
        checkInstance();
        AuthSingleton.name = name;
    }

    public static Uri getPicture() {
        checkInstance();
        return picture;
    }

    public static void setPicture(Uri picture) {
        checkInstance();
        AuthSingleton.picture = picture;
    }

    public static GoogleSignInResult getSignInResult() {
        checkInstance();
        return signInResult;
    }

    public static void setSignInResult(GoogleSignInResult signInResult) {
        checkInstance();
        AuthSingleton.signInResult = signInResult;
    }
}
