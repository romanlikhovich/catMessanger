package com.example.roman.socialmessaganger.commondata;

import java.util.ArrayList;

public class CommonData {
    private static CommonData ourInstance;
    private ArrayList<User> users;
    private boolean isDownload;
    private User userWhomSendMessage;
    private ArrayList<MyMessage> messages;

    public ArrayList<MyMessage> getMessages() {
        return messages;
    }

    public User getUserWhomSendMessage() {
        return userWhomSendMessage;
    }

    public void setUserWhomSendMessage(User userWhomSendMessage) {
        this.userWhomSendMessage = userWhomSendMessage;
    }

    public static CommonData getInstance() {
        if (ourInstance == null) {
            ourInstance = new CommonData();
        }
        return ourInstance;
    }

    private CommonData() {
        users = new ArrayList<>();
        messages = new ArrayList<>();
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

    public boolean isDownload() {
        return isDownload;
    }

}
