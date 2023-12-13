package com.example.congratulateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ChooseRoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        Button buttonHost = findViewById(R.id.buttonHost);
        Button buttonGuest = findViewById(R.id.buttonGuest);

        buttonHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserRole("host");
            }
        });

        buttonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserRole("guest");
            }
        });
    }

    private void updateUserRole(String role) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseDatabase.getInstance().getReference("users")
                    .child(userId)
                    .child("role")
                    .setValue(role)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            navigateToRoleSpecificActivity(role);
                        } else {
                            // Handle the error, such as a Toast message
                        }
                    });
        } else {
            // Prompt the user to log in since there is no user logged in.
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }




    private void navigateToRoleSpecificActivity(String role) {
        Intent intent;
        if ("host".equals(role)) {
            intent = new Intent(ChooseRoleActivity.this, HostOptionsActivity.class);
        } else {
            intent = new Intent(ChooseRoleActivity.this, GuestActivity.class);
            //  needs to be implemented
        }
        startActivity(intent);
        finish();
    }
}
