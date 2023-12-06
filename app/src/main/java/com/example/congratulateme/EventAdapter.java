package com.example.congratulateme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
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
        holder.tvHostName.setText(event.getHostName());
        holder.tvCategory.setText(event.getCategory());
        holder.tvDateTime.setText(event.getDateAndTime());
        // Set other properties...
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName;
        public TextView tvHostName;
        public TextView tvCategory;
        public TextView tvDateTime;
        // Other view holders...

        public ViewHolder(View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvHostName = itemView.findViewById(R.id.tvHostName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}

