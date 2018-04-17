package com.hfad.imdblogin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hfad.imdblogin.model.DatabaseHelper;
import com.hfad.imdblogin.R;

import java.util.Arrays;

public class ActivityLoginPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static int RC_SIGN_IN = 0;
    private static String TAG = "MAIN_ACTIVITY";
    private final String TAG1 = "FACELOG";
    //Connecting to google service
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;
    private SQLiteDatabase DB;
    private SQLiteOpenHelper openHelper;
    private Cursor cursor;

    //Auth to Firebase
    private FirebaseAuth mAuth;


    private EditText editEmailText;
    private EditText editPasswordText;
    private ProgressDialog progressDialog;

    //Listens for auth changes example when we sign on or out
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //See the auth status
        mAuth = FirebaseAuth.getInstance();

        openHelper = new DatabaseHelper(this);
        DB = openHelper.getReadableDatabase();

        /*//if the user is allready logged in
        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(ActivityLoginPage.this, ActivityHomepage.class));
        }*/

        progressDialog = new ProgressDialog(this);

        mCallbackManager = CallbackManager.Factory.create();

        editEmailText = findViewById(R.id.editEmailText);
        editPasswordText = findViewById(R.id.editPasswordText);


        findViewById(R.id.facebook_sign_in_button).setOnClickListener(this);
        findViewById(R.id.create_account_button).setOnClickListener(this);
        findViewById(R.id.cardViewLogin).setOnClickListener(this);
        findViewById(R.id.cardViewGoogle).setOnClickListener(this);
        findViewById(R.id.textViewSignup).setOnClickListener(this);


        //listener that respons for the activity
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user !=null){
                    Log.d("Auth","User logged in:" + user.getEmail());
                    startActivity(new Intent(ActivityLoginPage.this, ActivityHomepage.class));
                }
                else{
                    Log.d("Auth", "User logged out");
                }

            }
        };

        //What we want to recieve back from google how we want to send out request
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                /*what we want to recieve back from google, google send id token so we can send
                to firebase that we have legitiment login
                */
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode== RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()){
                //Google Sign in was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else {
                Toast.makeText(ActivityLoginPage.this,"Error, Auth went wrong",Toast.LENGTH_SHORT).show();
            }
        }

    }

    //we are using the tokenId to authenticate with firebase
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                // authenticating between ud and firebase

        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Sign in Succes
                    Log.d("AUTH","signInWithCredential: onComplete: " + task.isSuccessful());
                    FirebaseUser user = mAuth.getCurrentUser();

                }else{
                    Log.d("AUTH","signInWithCredential: failure: " + task.getException());
                    Toast.makeText(ActivityLoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI();
        }


        //Add listener when we start our application
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void updateUI() {
        Toast.makeText(ActivityLoginPage.this,"You are logged in",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(ActivityLoginPage.this, ActivityHomepage.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener !=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(ActivityLoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI();
                        }

                        // ...
                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(ActivityLoginPage.this, "Something went wrong",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardViewGoogle:
                signIn();
                break;
            case R.id.textViewSignup:
                startActivity(new Intent(ActivityLoginPage.this,ActivitySignUp.class));
                break;
            case R.id.cardViewLogin:
                userLogin();
                String email = editEmailText.getText().toString().trim();
                String password = editPasswordText.getText().toString().trim();
                cursor = DB.rawQuery("SELECT * FROM "
                        + DatabaseHelper.TABLE_USER + " WHERE "
                        + DatabaseHelper.getEmail() + "=? AND "
                        + DatabaseHelper.getPassword() + "=?", new String[]{email,password});
                if(cursor !=null){
                    if(cursor.getCount()>0){
                        cursor.moveToNext();
                        startActivity(new Intent(ActivityLoginPage.this, CreateAccountActivity.class));
                        Toast.makeText(getApplicationContext(), "Login Succesfully", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error couldn't login", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.facebook_sign_in_button:
                LoginManager.getInstance().logInWithReadPermissions(ActivityLoginPage.this, Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });
                break;
            case R.id.create_account_button:
                startActivity(new Intent(ActivityLoginPage.this, CreateAccountActivity.class));
                break;

        }

    }

    private void userLogin() {
        String email = editEmailText.getText().toString().trim();
        String password = editPasswordText.getText().toString().trim();

        progressDialog.setMessage("Logging in Please Wait...");
        progressDialog.show();



        if(email.isEmpty()){
            editEmailText.setError("Please type your email");
            editEmailText.requestFocus();

        if(password.isEmpty()){return;
        }
            editPasswordText.setError("Please enter your password");
            editPasswordText.requestFocus();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(ActivityLoginPage.this, ActivityHomepage.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

}
