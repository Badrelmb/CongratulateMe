package com.example.congratulateme;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateEventFragment extends Fragment {

    public CreateEventFragment() {
        // Required empty public constructor

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

                // TODO: Save the data to the database or pass it to the next activity
//                Event event = new Event(eventName, hostName, category, dateAndTime, address); // Assuming these are the fields in your Event class
//                listener.onEventCreated(event);


                // For now, just show a confirmation message
                Toast.makeText(getContext(), "Event created successfully!", Toast.LENGTH_SHORT).show();

                // TODO: Redirect to another activity if necessary
            }
        });

        return view;
    }
}
