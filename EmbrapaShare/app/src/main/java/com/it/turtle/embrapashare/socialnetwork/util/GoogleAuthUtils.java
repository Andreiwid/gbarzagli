package com.it.turtle.embrapashare.socialnetwork.util;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleAuthUtils {

    public static void googleSignIn(Activity activity, GoogleApiClient googleApiClient, int requestCode) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        activity.startActivityForResult(signInIntent, requestCode);
    }
}