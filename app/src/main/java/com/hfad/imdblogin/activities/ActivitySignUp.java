package com.hfad.imdblogin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hfad.imdblogin.R;

public class ActivitySignUp extends AppCompatActivity implements View.OnClickListener {


    private EditText editEmailText, editPasswordText;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editEmailText = findViewById(R.id.editEmailText);
        editPasswordText = findViewById(R.id.editPasswordText);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.goToLogin).setOnClickListener(this);
        findViewById(R.id.cardView).setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }


    private void registerUser() {
        String email = editEmailText.getText().toString().trim();
        String password = editPasswordText.getText().toString().trim();
        if(email.isEmpty()){
            editEmailText.setError("Email is required");
            editEmailText.requestFocus();
            return;
        }

        if(password.length()<6){
            editPasswordText.setError("Minimum length of password much be 6");
            editPasswordText.requestFocus();
            return;
        }

        //returns true if its not a valid email by setting an error
        /*if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmailText.setError("Please enter a valid email");
            editEmailText.requestFocus();
            return;
        }*/

        if(password.isEmpty()){
            editPasswordText.setError("Password is required");
            editPasswordText.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "User Registration Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cardView:
                registerUser();
                break;
            case R.id.goToLogin:
                startActivity(new Intent(ActivitySignUp.this, ActivityLoginPage.class));
                break;
        }

    }
}
