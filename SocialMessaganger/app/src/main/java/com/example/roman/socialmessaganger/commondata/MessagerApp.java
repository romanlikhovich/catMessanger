package com.example.roman.socialmessaganger.commondata;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class MessagerApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "Ht1RrrFkfLD1aWtNNHbiseJXMhw2fFz7Bi34qnQd",
                "ffAM9snIxMZIyFqX13tf6gdYJ0CS5ccknrkF98NA");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
