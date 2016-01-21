package com.example.roman.socialmessaganger.commondata;


import com.parse.ParseFile;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class User {

    private ParseFile userPhoto;
    private String id;
    private String username;
    private String name;
    private String email;
    private boolean isOnline;
    private ArrayList<User> friends;
    private GeoPoint location;


    public User (String id, String username, String name, String email, boolean isOnline,
                 GeoPoint location, ParseFile userPhoto) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.isOnline = isOnline;
        this.location = location;
        this.userPhoto = userPhoto;
        friends = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public ParseFile getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(ParseFile userPhoto) {
        this.userPhoto = userPhoto;
    }
}
