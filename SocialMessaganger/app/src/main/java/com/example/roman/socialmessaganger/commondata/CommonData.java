package com.example.roman.socialmessaganger.commondata;

import java.util.ArrayList;

public class CommonData {
    private static CommonData ourInstance;
    private ArrayList<User> users;
    private boolean isDownload;
    private User userWhomSendMessage;
    private ArrayList<MyMessage> messages;
    private boolean isMessageDownload;
    private ArrayList<MyMessage> allMessage;

    public ArrayList<MyMessage> getAllMessage() {
        return allMessage;
    }

    public ArrayList<MyMessage> getMessages() {
        return messages;
    }

    public boolean isMessageDownload() {
        return isMessageDownload;
    }

    public void setIsMessageDownload(boolean isMessageDownload) {
        this.isMessageDownload = isMessageDownload;
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
        isMessageDownload = false;
        allMessage = new ArrayList<>();
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
