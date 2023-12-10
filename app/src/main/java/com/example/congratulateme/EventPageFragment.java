package com.example.congratulateme;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventPageFragment extends Fragment {
    private RecyclerView postsRecyclerView;
    private PostsAdapter postsAdapter;
    private List<Post> postList;
    private DatabaseReference postsRef;
    private String eventId;
    private EditText editTextPostContent;
    private Button buttonAddPhoto;
    private Button buttonAddVideo;
    private Button buttonPost;

    public EventPageFragment() {
        // Required empty public constructor
    }

    public static EventPageFragment newInstance(Event event) {
        EventPageFragment fragment = new EventPageFragment();
        Bundle args = new Bundle();
        args.putSerializable("event", event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Event event = (Event) getArguments().getSerializable("event");
            if (event != null) {
                eventId = event.getId();
            }
        }
        // Initialize Firebase components
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://congratulateme-d2e26-default-rtdb.asia-southeast1.firebasedatabase.app/");
        postsRef = database.getReference("events").child(eventId).child("posts");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_page, container, false);

        editTextPostContent = view.findViewById(R.id.editTextPostContent);
        buttonAddPhoto = view.findViewById(R.id.buttonAddPhoto);
        buttonAddVideo = view.findViewById(R.id.buttonAddVideo);
        buttonPost = view.findViewById(R.id.buttonPost);

        postsRecyclerView = view.findViewById(R.id.postsRecyclerView);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postList = new ArrayList<>();
        postsAdapter = new PostsAdapter(postList);
        postsRecyclerView.setAdapter(postsAdapter);

        buttonAddPhoto.setOnClickListener(v -> selectMediaFromGallery("image/*"));
        buttonAddVideo.setOnClickListener(v -> selectMediaFromGallery("video/*"));

        buttonPost.setOnClickListener(v -> {
            String postContent = editTextPostContent.getText().toString();
            createNewPost(postContent);
        });

        fetchPosts();

        return view;
    }

    private void createNewPost(String content) {
        if (!content.trim().isEmpty()) {
            String postId = postsRef.push().getKey();
            Post newPost = new Post(content);
            newPost.setId(postId);
            newPost.setEventId(eventId);
            // Push the new post under the 'posts' node of the event
            postsRef.child(postId).setValue(newPost)
                    .addOnSuccessListener(aVoid -> {
                        editTextPostContent.setText("");
                        Toast.makeText(getContext(), "Post created successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to create post: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "Please write something to post.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchPosts() {
        // Changed this line to get the correct reference
        DatabaseReference eventPostsRef = FirebaseDatabase.getInstance()
                .getReference("events")
                .child(eventId)
                .child("posts");

        eventPostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    Log.d("EventPageFragment", "Post fetched: " + post.getContent());
                    postList.add(post);
                }
                postsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error fetching posts: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectMediaFromGallery(String mediaType) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType(mediaType);
        startActivityForResult(intent, 1); // The requestCode should be unique in your app
    }

    // Make sure to override onActivityResult if you are starting startActivityForResult
    // ...

    // Remember to define onActivityResult to handle the selected media
}
