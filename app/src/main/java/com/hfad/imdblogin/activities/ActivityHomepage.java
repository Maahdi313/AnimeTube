package com.hfad.imdblogin.activities;


import android.support.v4.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.hfad.imdblogin.R;
import com.hfad.imdblogin.adapters.RecyclerViewAdapter;
import com.hfad.imdblogin.fragment.FragmentCreateAccount;
import com.hfad.imdblogin.fragment.FragmentCreateAccount;
import com.hfad.imdblogin.model.Anime;
import com.hfad.imdblogin.model.AppDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityHomepage extends AppCompatActivity /*implements View.OnClickListener*/ {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String URL_JSON = "https://gist.githubusercontent.com/aws1994/f583d54e5af8e56173492d3f60dd5ebf/raw/c7796ba51d5a0d37fc756cf0fd14e54434c547bc/anime.json";
    private JsonArrayRequest ArrayRequest ;
    private RequestQueue requestQueue ;
    private List<Anime> animeList = new ArrayList<>();
    private RecyclerView recyclerView;

    public static FragmentManager fragmentManager;
    public static AppDatabase appDatabase;


//test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        fragmentManager = getSupportFragmentManager();
        appDatabase = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"userdb").allowMainThreadQueries().build();

        if(findViewById(R.id.fragment_container)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, new FragmentCreateAccount()).commit();
        }
/*
        findViewById(R.id.buttonLogout).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycler_view);
        jsoncall();

        //if the user is not logged, that means current user returns null
        if(mAuth.getCurrentUser()==null){
            //closing this activity
            finish();
            //starting main activity
            startActivity(new Intent(ActivityHomepage.this, ActivityLoginPage.class));
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    startActivity(new Intent(ActivityHomepage.this, ActivityLoginPage.class));
                }
            }
        };

    }
    public void jsoncall() {
    ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            JSONObject jsonObject = null;


            for (int i = 0 ; i<response.length();i++) {


                try {

                    jsonObject = response.getJSONObject(i);
                    Anime anime = new Anime();

                    anime.setName(jsonObject.getString("name"));
                    anime.setDescription(jsonObject.getString("description"));
                    anime.setRating(jsonObject.getString("Rating"));
                    anime.setNb_episode(jsonObject.getInt("episode"));
                    anime.setCategorie(jsonObject.getString("categorie"));
                    anime.setStudio(jsonObject.getString("studio"));
                    anime.setImage_url(jsonObject.getString("img"));

                    animeList.add(anime);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            Toast.makeText(ActivityHomepage.this,"Size of Liste "+String.valueOf(animeList.size()),Toast.LENGTH_SHORT).show();
            Toast.makeText(ActivityHomepage.this, animeList.get(1).toString(),Toast.LENGTH_SHORT).show();

            setRecyclerViewAdapter(animeList);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });


    requestQueue = Volley.newRequestQueue(ActivityHomepage.this);
        requestQueue.add(ArrayRequest);
    }

    public void setRecyclerViewAdapter (List<Anime> list) {

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,list) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }


    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogout:
                signOut();
                break;
        }*/
    }
}
