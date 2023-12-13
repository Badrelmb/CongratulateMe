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

public class ScanQRCodeFragment extends Fragment {

    private ActivityResultLauncher<ScanOptions> barcodeLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the barcode launcher
        barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                // Handle the scanned QR code (e.g., look up event by ID)
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
}
