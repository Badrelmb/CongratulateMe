package com.example.congratulateme;

import java.io.Serializable;

public class Event implements Serializable {
    private String id;
    private String eventName;
    private String hostName;
    private String category;
    private String date;
    private String time;
    private String address;

    // Default constructor is required for Firebase
    public Event() {
    }

    // Constructor with parameters
    public Event(String id, String eventName, String hostName, String category, String date, String time, String address) {
        this.id = id;
        this.eventName = eventName;
        this.hostName = hostName;
        this.category = category;
        this.date = date;
        this.time = time;
        this.address = address;
    }

    // Getters

    public String getId() {
        return id;
    }

    // Setter for ID
    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }


}


