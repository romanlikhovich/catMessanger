package com.example.roman.socialmessaganger.commondata;

import java.util.ArrayList;

public class CommonData {
    private static CommonData ourInstance;
    private ArrayList<User> users;
    private boolean isDownload;

    public static CommonData getInstance() {
        if (ourInstance == null) {
            ourInstance = new CommonData();
        }
        return ourInstance;
    }

    private CommonData() {
        users = new ArrayList<>();
        isDownload = false;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User findUserByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public boolean isDownload () {
        return isDownload;
    }
}
