package com.hfad.imdblogin.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hfad.imdblogin.R;

public class AnimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime2);

        //hide the default actionbar

        getSupportActionBar().hide();

        //Recieve data

        String name = getIntent().getExtras().getString("anime_name");
        String description = getIntent().getExtras().getString("anime_description");
        String studio = getIntent().getExtras().getString("anime_studio");
        String category = getIntent().getExtras().getString("anime_category");
        int episode = getIntent().getExtras().getInt("anime_nb_episode");
        String rating = getIntent().getExtras().getString("anime_rating");
        String image_url = getIntent().getExtras().getString("anime_img");


        //Init views

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_name = findViewById(R.id.aa_animeName);
        TextView tv_description = findViewById(R.id.aa_description);
        TextView tv_studio = findViewById(R.id.aa_studio);
        TextView tv_categorie = findViewById(R.id.aa_animeCategory);
        TextView tv_rating = findViewById(R.id.aa_animeRating);
        ImageView img = findViewById(R.id.aa_thumbnail);

        //setting values to each view

        tv_name.setText(name);
        tv_description.setText(description);
        tv_studio.setText(studio);
        tv_categorie.setText(category);
        tv_rating.setText(rating);

        collapsingToolbarLayout.setTitle(name);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        //set image using Glide
        Glide
                .with(this)
                .load(image_url)
                .apply(requestOptions)
                .into(img);


    }
}
