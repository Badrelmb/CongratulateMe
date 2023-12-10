package com.example.congratulateme;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
        // Assuming Post class has these methods
        holder.textViewPostContent.setText(post.getContent());
        // Set other post details to holder views
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPostContent;
        // Define other views in the item layout

        public PostViewHolder(View itemView) {
            super(itemView);
            textViewPostContent = itemView.findViewById(R.id.text_view_post);
            // Initialize other views in the item layout
        }
    }
}