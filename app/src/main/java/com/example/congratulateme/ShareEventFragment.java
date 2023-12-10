package com.example.congratulateme;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ShareEventFragment extends Fragment {
    private Event event; // This should be passed to the Fragment through the arguments
    private ImageView qrCodeImageView;
    private TextView eventNameTextView;
    private TextView eventHostTextView;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventLocationTextView;

    public static ShareEventFragment newInstance(Event event) {
        ShareEventFragment fragment = new ShareEventFragment();
        Bundle args = new Bundle();
        args.putSerializable("event", event); // Make sure Event implements Serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            event = (Event) getArguments().getSerializable("event");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_event, container, false);

        qrCodeImageView = view.findViewById(R.id.qrCodeImageView);
        eventNameTextView = view.findViewById(R.id.eventNameTextView);
        eventHostTextView = view.findViewById(R.id.eventHostTextView);
        eventDateTextView = view.findViewById(R.id.eventDateTextView);
        eventTimeTextView = view.findViewById(R.id.eventTimeTextView);
        eventLocationTextView = view.findViewById(R.id.eventLocationTextView);

        if (event != null) {
            eventNameTextView.setText(event.getEventName());
            eventHostTextView.setText(event.getHostName());
            eventDateTextView.setText(event.getDate());
            eventTimeTextView.setText(event.getTime());
            eventLocationTextView.setText(event.getAddress());

            generateQRCode(event.getId());
        }
        updateViews();
        return view;
    }
    private void updateViews() {
        if (event != null) {
            eventNameTextView.setText(event.getEventName());
            eventDateTextView.setText(event.getDate());
            eventTimeTextView.setText(event.getTime());
            generateQRCode(event.getId()); // You'll need an ID or some unique data for the event
        }
    }
    private void generateQRCode(String eventData) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(eventData, BarcodeFormat.QR_CODE, 400, 400);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
