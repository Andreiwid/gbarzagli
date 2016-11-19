package com.it.turtle.embrapashare.socialnetwork.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.it.turtle.embrapashare.R;
import com.it.turtle.embrapashare.socialnetwork.models.AuthSingleton;

public class ConnectActivity extends LoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();


    }

    private void initView() {
        setContentView(R.layout.activity_connect);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
    }


    @Override
    protected void onGoogleConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onGoogleSignInFailure(GoogleSignInResult result) {

    }

    @Override
    protected void onGoogleSignInSuccess(GoogleSignInResult result) {
        GoogleSignInAccount acct = result.getSignInAccount();

        AuthSingleton.getInstance().setName(acct.getDisplayName());
        AuthSingleton.getInstance().setPicture(acct.getPhotoUrl());
        AuthSingleton.getInstance().setSignInResult(result);

        Intent intent = new Intent(getApplicationContext(), CulturePickerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSignOut() {
    }

    private void checkPermissions () {
    }
}
