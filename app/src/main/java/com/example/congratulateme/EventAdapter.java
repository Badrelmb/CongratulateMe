package com.example.congratulateme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> eventList;
    private OnEventClickListener clickListener;

    // Define an interface for the click listener
    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    // Modify the constructor to accept the OnEventClickListener as a parameter
    public EventAdapter(List<Event> eventList, OnEventClickListener clickListener) {
        this.eventList = eventList;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvEventName.setText(event.getEventName());
        holder.tvDate.setText(event.getDate());
        holder.tvTime.setText(event.getTime());

        // Set the click listener for the entire item view
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onEventClick(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName;
        public TextView tvDate;
        public TextView tvTime;
        // Other view holders...

        public ViewHolder(View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            // Initialize other views here if needed
        }
    }
}
