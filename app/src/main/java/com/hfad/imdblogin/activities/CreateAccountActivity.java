package com.hfad.imdblogin.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hfad.imdblogin.model.DatabaseHelper;
import com.hfad.imdblogin.R;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    EditText txtfName, txtlName, txtPass, txtEmail, txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        openHelper = new DatabaseHelper(this);
        databaseHelper = new DatabaseHelper(this);

        txtfName = findViewById(R.id.txt_firstName);
        txtlName = findViewById(R.id.txt_lastName);
        txtPass = findViewById(R.id.txt_password);
        txtEmail = findViewById(R.id.txt_email);
        txtPhone = findViewById(R.id.txt_phone);

        findViewById(R.id.btnlogin).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_register:
                db = openHelper.getWritableDatabase();
                String fname = txtfName.getText().toString();
                String lname = txtlName.getText().toString();
                String pass = txtPass.getText().toString();
                String email = txtEmail.getText().toString();
                String phone = txtPhone.getText().toString();

                databaseHelper.insert(-1, fname,lname,pass,email,phone);
                Toast.makeText(getApplicationContext(), "Registration successfull", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnlogin:
                startActivity(new Intent(CreateAccountActivity.this, ActivityLoginPage.class));
                break;
        }
    }
}
