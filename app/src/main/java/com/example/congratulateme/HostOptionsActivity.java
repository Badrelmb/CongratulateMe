package com.example.congratulateme;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    private void fetchHostedEvents() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("events");

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            eventsRef.orderByChild("hostId").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Event> hostedEvents = new ArrayList<>();
                    for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                        Event event = eventSnapshot.getValue(Event.class);
                        hostedEvents.add(event);
                    }
                    MyEventsFragment myEventsFragment = (MyEventsFragment) getSupportFragmentManager()
                            .findFragmentByTag("MY_EVENTS_FRAGMENT_TAG");
                    if (myEventsFragment != null) {
                        myEventsFragment.updateEventList(hostedEvents);
                    } else {
                        // The fragment is not found, handle this case
                    }

                    myEventsFragment.updateEventList(hostedEvents);
                    // Now you have an array of events that the current user is hosting
                    // You can pass this array to the MyEventsFragment to display
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle the error
                }
            });
        }
    }

}


