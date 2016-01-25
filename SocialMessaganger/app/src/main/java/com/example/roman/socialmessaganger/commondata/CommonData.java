package com.example.roman.socialmessaganger.commondata;

import android.app.Fragment;
import android.app.FragmentTransaction;

import java.util.ArrayList;

public class CommonData {
    private static CommonData ourInstance;
    private ArrayList<User> users;
    private boolean isDownload;
    private User userWhomSendMessage;
    private ArrayList<MyMessage> allMessage;
    private Fragment fragment;
    private boolean firstRun;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public ArrayList<MyMessage> getAllMessage() {
        return allMessage;
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
        isDownload = false;
        allMessage = new ArrayList<>();
        firstRun = true;
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

    public User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public boolean isFirstRun() {
        return firstRun;
    }

    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }
}
