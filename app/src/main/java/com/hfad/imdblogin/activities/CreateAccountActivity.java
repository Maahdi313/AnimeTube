package com.hfad.imdblogin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.hfad.imdblogin.R;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        findViewById(R.id.btnlogin).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnlogin:
                startActivity(new Intent(CreateAccountActivity.this, ActivityLoginPage.class));
                break;
        }
    }
}
