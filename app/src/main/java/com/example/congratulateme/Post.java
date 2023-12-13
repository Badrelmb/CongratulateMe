package com.example.congratulateme;

public class Post {
    private String text; // This field can include text, image URLs, or video URLs.
    private String mediaUrl; // Separate field for media if needed
    private String id;
    private String eventId;
    private String authorId;
    private long timestamp;

    public Post() {
        // Empty constructor is required for Firebase to deserialize data
    }

    // Add getters and setters for the properties
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // If you decide to keep the text field as the main content holder,
    // then the constructor should also reflect this.
    public Post(String text) {
        this.text = text;
    }
}
