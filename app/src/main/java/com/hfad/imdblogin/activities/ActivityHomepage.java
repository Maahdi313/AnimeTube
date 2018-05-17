package com.hfad.imdblogin.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hfad.imdblogin.Notification.NotificationActivity;
import com.hfad.imdblogin.R;
import com.hfad.imdblogin.adapters.RecyclerViewAdapter;
import com.hfad.imdblogin.model.Anime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ActivityHomepage extends AppCompatActivity{

    private String URL_JSON = "https://gist.githubusercontent.com/aws1994/f583d54e5af8e56173492d3f60dd5ebf/raw/c7796ba51d5a0d37fc756cf0fd14e54434c547bc/anime.json";
    private JsonArrayRequest ArrayRequest ;
    private RequestQueue requestQueue ;
    private List<Anime> animeList = new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.recycler_view);
        jsoncall();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.item_account:
                Intent loginIntent = new Intent(ActivityHomepage.this, ActivityLogin.class);
                startActivity(loginIntent);
                break;
            case R.id.item_notification:
                Intent intentNotification = new Intent(ActivityHomepage.this, NotificationActivity.class);
                startActivity(intentNotification);
                break;
        }
        return super.onOptionsItemSelected(item);
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


                //Toast.makeText(ActivityHomepage.this,"Size of Liste "+String.valueOf(animeList.size()),Toast.LENGTH_SHORT).show();
                //Toast.makeText(ActivityHomepage.this, animeList.get(1).toString(),Toast.LENGTH_SHORT).show();

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

}
