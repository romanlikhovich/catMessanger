package com.example.roman.socialmessaganger.commondata;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class MessagerApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "Sanz9JfGOG1xneTTDLZPGedOsBWEbQ15kJ13Pfsi",
                "kraqHLb0wSTFkDDFQ7Y4Jxb2hgwdEVsUwF5D0Ftt");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
