package com.example.congratulateme;

public class Post {
    // Define the properties of a Post
    private String text;
    private String mediaUrl;
    private String id;

    private String eventId;
    private String content;
    private String authorId;
    private long timestamp;

    // Empty constructor is required for Firebase to deserialize data
    public Post() {
    }



    // Add getters and setters for your properties
    public String getText() {
        return text;
    } // Getters and setters for each field
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setText(String text) {
        this.text = text;
    }

    // Add a constructor if necessary
    public Post(String text) {
        this.text = text;
    }


}

