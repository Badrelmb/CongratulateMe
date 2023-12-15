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


public class GuestActivity extends AppCompatActivity implements ScanQRCodeFragment.OnQRCodeScannedListener {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        viewPager2 = findViewById(R.id.guest_viewpager);
        tabLayout = findViewById(R.id.guest_tabs);

        // Set up the ViewPager with the sections adapter.
        viewPager2.setAdapter(new FragmentAdapter());
        // Connect the TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("Scan QR Code");
            } else {
                tab.setText("My Guest Events");
            }
        }).attach();

    }

    // This method is called when a QR code is scanned
    @Override
    public void onQRCodeScanned(Event event) {
        // Add event to MyGuestEventsFragment
        // Switch to MyGuestEventsFragment
    }


    private class FragmentAdapter extends FragmentStateAdapter {
        public FragmentAdapter() {
            super(GuestActivity.this);
        }
        @Override
        public Fragment createFragment(int position) {
            // Return a NEW fragment instance in createFragment(int)
            if (position == 0) {
                ScanQRCodeFragment scanFragment = new ScanQRCodeFragment();
                // Set the listener for the ScanQRCodeFragment
                scanFragment.setOnQRCodeScannedListener(GuestActivity.this);
                return scanFragment;
            } else {
                return new MyGuestEventsFragment();
            }
        }

        @Override
        public int getItemCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
