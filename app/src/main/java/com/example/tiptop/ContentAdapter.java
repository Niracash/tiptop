package com.example.tiptop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private List<Content> allContents;
    private Context context;
    public ContentAdapter(Context context, List<Content> contents){
        this.allContents = contents;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Content content = allContents.get(position);
        holder.title.setText(content.getTitle());
        //holder.title.setText(allContents.get(position).getTitle());
        //Picasso.get().load(allContents.get(position).getThumbnailUrl()).into(holder.contentImage);

        // Ensure the URL uses HTTPS
        String imageUrl = content.getThumbnailUrl().replace("http:", "https:");

        Picasso.get().load(imageUrl).into(holder.contentImage);



    }

    @Override
    public int getItemCount() {
        return allContents.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView contentImage;
        TextView title;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentImage = itemView.findViewById(R.id.contentThumbnail);
            title = itemView.findViewById(R.id.contentTitle);
            view = itemView;
        }
    }
}
