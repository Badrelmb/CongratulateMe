package com.example.congratulateme;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateEventFragment extends Fragment {
    private DatabaseReference databaseReference;
    public CreateEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance("https://congratulateme-d2e26-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("events");
    }
    public interface OnEventCreatedListener {
        void onEventCreated(Event event);
    }
    private OnEventCreatedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnEventCreatedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnEventCreatedListener");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        // Initialize the Spinner
        Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.event_categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapter);

        // Set up the confirm button
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the data from the form elements
                String eventName = ((EditText)view.findViewById(R.id.editTextEventName)).getText().toString();
                String hostName = ((EditText)view.findViewById(R.id.editTextHostName)).getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();
                String address = ((EditText)view.findViewById(R.id.editTextAddress)).getText().toString();

                // Collect date and time from the DatePicker and TimePicker
                // Inside your buttonConfirm.setOnClickListener
                DatePicker datePicker = view.findViewById(R.id.datePickerEvent);
                TimePicker timePicker = view.findViewById(R.id.timePickerEvent);

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Month is 0-based
                int year = datePicker.getYear();

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                // Generate a unique key for the event
                String eventId = databaseReference.push().getKey();
                String date = day + "/" + month + "/" + year;
                String time = hour + ":" + minute;

                Event event = new Event(eventId, eventName, hostName, category, date, time, address);


                event.setId(eventId);

                databaseReference.child(eventId).setValue(event)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(getContext(), "Event created successfully!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(getContext(), "Failed to create event: " + e.getMessage(), Toast.LENGTH_SHORT).show());

                databaseReference.child(eventId).child("posts").setValue(new HashMap<String, Object>()); // Initialize empty posts node
                // For now, just show a confirmation message
                Toast.makeText(getContext(), "Event created successfully!", Toast.LENGTH_SHORT).show();

                // TODO: Redirect to another activity if necessary
            }
        });

        return view;
    }
}
