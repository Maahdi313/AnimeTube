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

import com.hfad.imdblogin.R;
import com.hfad.imdblogin.helper.InputValidation;
import com.hfad.imdblogin.model.User;
import com.hfad.imdblogin.persistence.DatabaseHelper;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = ActivityRegister.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews()
    {
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutName = findViewById(R.id.til_name);
        textInputLayoutUsername = findViewById(R.id.til_username);
        textInputLayoutPassword = findViewById(R.id.til_password);
        textInputLayoutConfirmPassword = findViewById(R.id.til_cornfirm_password);


        textInputEditTextName = findViewById(R.id.tiet_name);
        textInputEditTextUsername = findViewById(R.id.tiet_username);
        textInputEditTextPassword = findViewById(R.id.tiet_password);
        textInputEditTextConfirmPassword= findViewById(R.id.tiet_confirm_password);

        appCompatButtonRegister = findViewById(R.id.acbtn_register);

        appCompatTextViewLogin = findViewById(R.id.actv_login);

    }

    private void initListeners()
    {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLogin.setOnClickListener(this);
    }

    private void initObjects()
    {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.acbtn_register:
                postDataToSQLite();
                break;
            case R.id.actv_login:
                finish();
                break;
        }
    }

    private void postDataToSQLite()
    {
        if(!inputValidation.isInputETFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name)))
        {
            return;
        }
        if(!inputValidation.isInputETFilled(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username)))
        {
            return;
        }
        if(!inputValidation.isInputETUsername(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username)))
        {
            return;
        }
        if(!inputValidation.isInputETFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password)))
        {
            return;
        }
        if(!inputValidation.isInputETMatches(textInputEditTextPassword, textInputEditTextConfirmPassword, textInputLayoutConfirmPassword, getString(R.string.error_password_match)))
        {
            return;
        }

        if(!databaseHelper.checkUser(textInputEditTextUsername.getText().toString().trim()))
        {
            user.setName(textInputEditTextName.getText().toString().trim());
            user.setUsername(textInputEditTextUsername.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_username_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText()
    {
        textInputEditTextName.setText(null);
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);

    }
}
