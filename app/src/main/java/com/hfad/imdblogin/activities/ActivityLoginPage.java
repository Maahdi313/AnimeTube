package com.hfad.imdblogin.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.hfad.imdblogin.R;

public class ActivityLoginPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //See the auth statu

        findViewById(R.id.create_account_button).setOnClickListener(this);
        findViewById(R.id.cardViewLogin).setOnClickListener(this);

        findViewById(R.id.textViewSignup).setOnClickListener(this);

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(ActivityLoginPage.this, "Something went wrong",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewSignup:
                startActivity(new Intent(ActivityLoginPage.this,ActivitySignUp.class));
                break;
            case R.id.create_account_button:
                startActivity(new Intent(ActivityLoginPage.this, CreateAccountActivity.class));
                break;

        }

    }

}
