package com.example.newproject;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private Context mcontext;
    private List<book> muploads;


   public ImageAdapter(Context context,List<book> books)
   {
       mcontext=context;
       muploads=books;
   }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.list,parent,false);
        return new  ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
       book boook = muploads.get(position);
       holder.tauthor.setText(boook.getAuthor());
       holder.ttitle.setText(boook.getTitle());
       holder.tdescription.setText(boook.getDescription());
       Glide.with(mcontext)
               .load(boook.getUrl())
               .centerCrop()
               .fitCenter()
               .into(holder.imageview);

    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tauthor,ttitle,tdescription;
        public ImageView imageview;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ttitle = itemView.findViewById(R.id.textView4);
            tdescription=itemView.findViewById(R.id.textView5);
            tauthor = itemView.findViewById(R.id.textView6);
            imageview =itemView.findViewById(R.id.imageView);
        }
    }

}
