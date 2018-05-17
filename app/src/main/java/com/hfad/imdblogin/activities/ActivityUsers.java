package com.hfad.imdblogin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hfad.imdblogin.R;

public class ActivityUsers extends AppCompatActivity {

    private TextView textViewName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        textViewName = findViewById(R.id.text1);
        String nameFromIntent = getIntent().getStringExtra("NAME");
        textViewName.setText("Welcome " + nameFromIntent);
    }

}
