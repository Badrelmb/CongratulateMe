package com.example.congratulateme;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventPageFragment extends Fragment {


    private RecyclerView postsRecyclerView;
    private ActivityResultLauncher<String> mGetContent;
    private PostsAdapter postsAdapter;
    private List<Post> postList;
    private DatabaseReference postsRef;
    private String eventId;
    private EditText editTextPostContent;
    private Button buttonAddPhoto;
    private Button buttonAddVideo;
    private Button buttonPost;
    private String uploadedImageUrl = null;

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
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                uploadImageToFirebase(uri);
            }
        });

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

        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will launch the gallery and the result will be handled in the ActivityResultCallback<Uri> you defined
                mGetContent.launch("image/*");
            }
        });
//        buttonAddVideo.setOnClickListener(v -> selectMediaFromGallery("video/*"));

        buttonPost.setOnClickListener(v -> {
            String postContent = editTextPostContent.getText().toString();

            createNewPost(postContent, uploadedImageUrl);
            uploadedImageUrl = null; // Reset after using
        });

        fetchPosts();

        return view;
    }

    private void uploadImageToFirebase(Uri imageUri) {
        String imageName = "images/" + UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(imageName);

        imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            // Get the download URL from the uploaded image
            Task<Uri> downloadUrlTask = taskSnapshot.getStorage().getDownloadUrl();
            downloadUrlTask.addOnSuccessListener(downloadUri -> {
                // Now, you have the download URL, create a new Post object or update an existing one
                String postContent = editTextPostContent.getText().toString();
                createNewPost(postContent, downloadUri.toString());
                uploadedImageUrl = downloadUri.toString();

            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void createNewPost(String content, @Nullable String imageUrl) {
        String postId = postsRef.push().getKey();
        if (postId != null) {
            Post newPost = new Post(content);
            newPost.setId(postId);
            newPost.setEventId(eventId);
            newPost.setTimestamp(System.currentTimeMillis());

            if (imageUrl != null) {
                newPost.setMediaUrl(imageUrl);
            }

            // Here, set the authorId, which is missing in your current implementation
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                newPost.setAuthorId(currentUser.getUid());
            } else {
                // Handle the case where the user is not logged in or you cannot get the user ID
                newPost.setAuthorId("defaultUserId");
            }

            // Push the new post under the 'posts' node of the event
            postsRef.child(postId).setValue(newPost)
                    .addOnSuccessListener(aVoid -> {
                        editTextPostContent.setText("");
                        Toast.makeText(getContext(), "Post created successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to create post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Error: Could not generate a post ID.", Toast.LENGTH_SHORT).show();
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
                    Log.d("EventPageFragment", "Post fetched: " + post.getText());
                    postList.add(post);
                }
                // Run on UI thread
                postsRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        postsAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error fetching posts: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
