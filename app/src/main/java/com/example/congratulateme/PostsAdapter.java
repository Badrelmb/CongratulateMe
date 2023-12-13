package com.example.congratulateme;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private final List<Post> postList;

    public PostsAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = postList.get(position);
        // Bind the user ID to its TextView
        holder.textViewUserId.setText(post.getAuthorId()); // Make sure you have a getter method for authorId in your Post class
        holder.textViewPostContent.setText(post.getText());
        // Check if the post has text content and bind it
        if (post.getText() != null && !post.getText().isEmpty()) {
            holder.textViewPostContent.setText(post.getText());
            holder.textViewPostContent.setVisibility(View.VISIBLE);
        } else {
            holder.textViewPostContent.setVisibility(View.GONE);
        }

        // Check if the post has an image URL and display it
        if (post.getMediaUrl() != null && !post.getMediaUrl().isEmpty()) {
            holder.imageViewPostMedia.setVisibility(View.VISIBLE);
            // Use Glide or another library to load the image from the URL into the ImageView
            Glide.with(holder.itemView.getContext())
                    .load(post.getMediaUrl())
                    .into(holder.imageViewPostMedia);
        } else {
            holder.imageViewPostMedia.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPostContent;
        TextView textViewUserId;
        ImageView imageViewPostMedia;
        // Define other views in the item layout

        public PostViewHolder(View itemView) {
            super(itemView);
            textViewUserId = itemView.findViewById(R.id.text_view_user_id);
            textViewPostContent = itemView.findViewById(R.id.text_view_post);
            imageViewPostMedia = itemView.findViewById(R.id.image_view_post_media);
            // Initialize other views in the item layout
        }
    }

    public static class GuestActivity {
    }
}