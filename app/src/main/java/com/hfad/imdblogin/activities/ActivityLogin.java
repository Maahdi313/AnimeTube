package com.hfad.imdblogin.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.hfad.imdblogin.R;
import com.hfad.imdblogin.helper.InputValidation;
import com.hfad.imdblogin.persistence.DatabaseHelper;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = ActivityLogin.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView appCompatTextViewRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews()
    {
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutUsername = findViewById(R.id.til_username);
        textInputLayoutPassword = findViewById(R.id.til_password);

        textInputEditTextUsername = findViewById(R.id.tiet_username);
        textInputEditTextPassword = findViewById(R.id.tiet_password);

        appCompatButtonLogin = findViewById(R.id.acbtn_login);

        appCompatTextViewRegister = findViewById(R.id.actv_register);
    }

    private void initListeners()
    {
        appCompatButtonLogin.setOnClickListener(this);
        appCompatTextViewRegister.setOnClickListener(this);
    }

    private void initObjects()
    {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.acbtn_login:
                verifyFromSQLite();
                break;
            case R.id.actv_register:
                Intent intentRegister = new Intent(getApplicationContext(), ActivityRegister.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite()
    {
        if(!inputValidation.isInputETFilled(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username)))
        {
            return;
        }
        if(!inputValidation.isInputETUsername(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username)))
        {
            return;
        }
        if(!inputValidation.isInputETFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_username)))
        {
            return;
        }

        if(databaseHelper.checkUser(textInputEditTextUsername.getText().toString().trim(),
                textInputEditTextPassword.getText().toString().trim()))
        {
            Intent accountsIntent = new Intent(activity, ActivityHomepage.class);
            //accountsIntent.putExtra("USERNAME", textInputEditTextUsername.getText().toString().trim());
            //emptyInputEditText();
            Toast.makeText(ActivityLogin.this, "Successfully signed in!!!!", Toast.LENGTH_SHORT).show();
            startActivity(accountsIntent);
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_wrong_username_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText()
    {
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);

    }

}
