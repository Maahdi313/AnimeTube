package com.hfad.imdblogin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hfad.imdblogin.R;
import com.hfad.imdblogin.activities.AnimeActivity;
import com.hfad.imdblogin.model.Anime;

import java.util.List;

/*
The RecyclerView model, several different components work together to display the data
The RecyclerView fills itself with Views provided by a layout manager that we provide,
here we have used LinearLayoutManager.
The views in the list are represented by view holder objects. These objects are instances of class
you define by extending RecyclerView.ViewHolder.


 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Anime> mData;

    //Provides type independent options to customize loads with Glide.
    RequestOptions option;

    public RecyclerViewAdapter(Context mContent, List<Anime> mData) {
        this.mContext = mContent;
        this.mData = mData;

        //Request option for glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    /*
    Called when RecyclerView needs a new RecyclerView.ViewHolder of given type to represent an item
    Everytime a new ViewHolder is created for an item this method is called.
    */

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        //Converts an XML layout into ViewGroups and Widgets
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.anime_row_item,parent, false);

        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,AnimeActivity.class);
                intent.putExtra("anime_name", mData.get(viewHolder.getAdapterPosition()).getName());
                intent.putExtra("anime_description", mData.get(viewHolder.getAdapterPosition()).getDescription());
                intent.putExtra("anime_studio", mData.get(viewHolder.getAdapterPosition()).getStudio());
                intent.putExtra("anime_category", mData.get(viewHolder.getAdapterPosition()).getCategorie());
                intent.putExtra("anime_nb_episode", mData.get(viewHolder.getAdapterPosition()).getNb_episode());
                intent.putExtra("anime_rating", mData.get(viewHolder.getAdapterPosition()).getRating());
                intent.putExtra("anime_img", mData.get(viewHolder.getAdapterPosition()).getImage_url());
                mContext.startActivity(intent);

            }
        });

        return viewHolder;
    }

    @Override
    /*
    This method is called after onCreateViewHolder
    This binds the data to the MyViewHolder, means that we will show the actual data to the RecyclerView

    As the user Scrolls through the list, the RecyclerView takes the off screen views and rebinds them
    to the data.
    It does by assigning the view holder to a position, and calling the adapter's onBindViewHolder() method.

    */
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_rating.setText(mData.get(position).getRating());
        holder.tv_studio.setText(mData.get(position).getStudio());
        holder.tv_category.setText(mData.get(position).getCategorie());

        //load Image from internet and set into ImageView using Glide
        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(option).into(holder.img_thumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /*
    View holder is in charge displaying single item with a view. When my view shows an anime series collection
    each holder represent a single episode.
    RecyclerView creates only as many view holders as needed to display on the screen + a few extra.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_rating,tv_studio,tv_category;
        ImageView img_thumbnail;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.ar_container);
            tv_name = itemView.findViewById(R.id.animeName);
            tv_category = itemView.findViewById(R.id.animeCategory);
            tv_studio = itemView.findViewById(R.id.animeRating);
            tv_rating = itemView.findViewById(R.id.studio);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }


}
