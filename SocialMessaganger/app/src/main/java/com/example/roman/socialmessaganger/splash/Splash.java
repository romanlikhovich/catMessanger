package com.example.roman.socialmessaganger.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.roman.socialmessaganger.activity.Login;
import com.example.roman.socialmessaganger.R;
import com.example.roman.socialmessaganger.activity.UserMain;
import com.parse.ParseUser;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(5 * 1000);
                    if (ParseUser.getCurrentUser() == null) {
                        Intent i = new Intent(getBaseContext(), Login.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getBaseContext(), UserMain.class);
                        startActivity(i);
                    }
                    finish();
                } catch (Exception e) {

                }
            }
        };
        background.start();
    }
}
