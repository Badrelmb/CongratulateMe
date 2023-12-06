package com.example.congratulateme;

public class Event {
    private String eventName;
    private String hostName;
    private String category;
    private String dateAndTime;
    private String address;

    public Event(String eventName, String hostName, String category, String dateAndTime, String address) {
        this.eventName = eventName;
        this.hostName = hostName;
        this.category = category;
        this.dateAndTime = dateAndTime;
        this.address = address;
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getAddress() {
        return address;
    }

    // Optionally, you can also include setters if needed
    // ...
}
