package com.hfad.imdblogin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hfad.imdblogin.R;
import com.hfad.imdblogin.model.Anime;
import com.hfad.imdblogin.R;

import java.util.List;

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
    //Called when RecyclerView needs a new RecyclerView.ViewHolder of given type to represent an item
    //Everytime a new ViewHolder is created for an item this method is called
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        //Converts an XML layout into ViewGroups and Widgets
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.anime_row_item,parent, false);

        return new MyViewHolder(view);
    }

    @Override
    /*
    This method is called after onCreateViewHolder
    This binds he data to the MyViewHolder, means we will show the actual data to the RecyclerView
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_rating,tv_studio,tv_category;
        ImageView img_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.animeName);
            tv_category = itemView.findViewById(R.id.animeCategory);
            tv_studio = itemView.findViewById(R.id.studio);
            tv_rating = itemView.findViewById(R.id.animeRating);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }


}
