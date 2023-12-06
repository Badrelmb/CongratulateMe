package com.example.congratulateme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HostOptionsActivity extends AppCompatActivity implements CreateEventFragment.OnEventCreatedListener{

    TabLayout tabLayout;
    ViewPager2 viewPager;
    HostOptionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_options);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new HostOptionsAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "My Events" : "Create New Event")
        ).attach();

    }



    private class HostOptionsAdapter extends FragmentStateAdapter {
        public HostOptionsAdapter(AppCompatActivity activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            return position == 0 ? new MyEventsFragment() : new CreateEventFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
    // Implement the OnEventCreatedListener method
    @Override
    public void onEventCreated(Event event) {
        // Handle the event creation here
        // For example, update a list or show a message
    }
}


