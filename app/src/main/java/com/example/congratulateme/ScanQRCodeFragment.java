package com.example.congratulateme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ScanQRCodeFragment extends Fragment {
    // Define an interface for callback
    public interface OnQRCodeScannedListener {
        void onQRCodeScanned(Event event);
    }

    private OnQRCodeScannedListener qrCodeScannedListener;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the barcode launcher
        barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String eventId = result.getContents();
                fetchEventFromFirebase(eventId);
            }
        });
    }private void fetchEventFromFirebase(String eventId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events"); // Replace "events" with your actual Firebase database path

        databaseReference.child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assuming Event class matches the structure in your database
                    Event event = dataSnapshot.getValue(Event.class);
                    // Notify the activity (if the listener is set)
                    if (qrCodeScannedListener != null && event != null) {
                        qrCodeScannedListener.onQRCodeScanned(event);
                    }
                } else {
                    // Handle the case where the event does not exist
                    Toast.makeText(getContext(), "QR code does not match any event", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
                Toast.makeText(getContext(), "Error fetching event data", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Replace R.layout.fragment_scan_qr_code with your actual layout file
        return inflater.inflate(R.layout.fragment_scan_q_r_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set up any buttons or actions to initiate the QR scan
        initiateQRScanner();
    }

    private void initiateQRScanner() {
        // Set options for the barcode scanner
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setPrompt("Scan QR code to access event");
        options.setCameraId(0); // Use a specific camera of the device
        options.setBeepEnabled(false); // Whether you want to be notified via beep
        options.setBarcodeImageEnabled(true); // Whether to save the barcode image
        barcodeLauncher.launch(options);
    }

    // Method to set the listener from the Activity
    public void setOnQRCodeScannedListener(OnQRCodeScannedListener listener) {
        this.qrCodeScannedListener = listener;
    }
}
