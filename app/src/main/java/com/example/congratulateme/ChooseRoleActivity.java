package com.example.congratulateme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


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
                // Navigate to Host activity
                Intent intent = new Intent(ChooseRoleActivity.this, HostOptionsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to Guest activity or perform guest-specific logic
            }
        });
    }
}