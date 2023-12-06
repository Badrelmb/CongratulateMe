package com.example.congratulateme;

public class User {
    public String name;
    public String email;
    public String id;
    public String password;
    public String getPassword() {
        return password;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String id, String password) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
    }
}

