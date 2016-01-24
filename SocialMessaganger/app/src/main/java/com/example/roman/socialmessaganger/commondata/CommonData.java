package com.example.roman.socialmessaganger.commondata;

import android.app.Fragment;
import android.app.FragmentTransaction;

import java.util.ArrayList;

public class CommonData {
    private static CommonData ourInstance;
    private ArrayList<User> users;
    private boolean isDownload;
    private User userWhomSendMessage;
    private ArrayList<MyMessage> messages;
    private boolean isMessageDownload;
    private ArrayList<MyMessage> allMessage;
    private Fragment fragment;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

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

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public boolean isDownload() {
        return isDownload;
    }

}
