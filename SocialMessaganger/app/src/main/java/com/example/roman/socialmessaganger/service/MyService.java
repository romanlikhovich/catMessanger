package com.example.roman.socialmessaganger.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.example.roman.socialmessaganger.binder.MyBinder;
import com.example.roman.socialmessaganger.commondata.CommonData;
import com.example.roman.socialmessaganger.other.UpdateActivity;
import com.example.roman.socialmessaganger.commondata.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.osmdroid.util.GeoPoint;

import java.util.List;

public class MyService extends Service implements Runnable {
    private UpdateActivity activity;
    private boolean firstRun;

    public MyService() {
    }

    public void setActivity(UpdateActivity activity) {
        this.activity = activity;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(this).start();
        firstRun = true;

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    ParseQuery<ParseObject> = new ParseQuery<>()
//                }
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void run() {
        while (ParseUser.getCurrentUser() !=null){
            if (firstRun) {
                SystemClock.sleep(1000);
            }
//            download information from parse one on minute or in settings time;
            CommonData.getInstance().getUsers().clear();
            CommonData.getInstance().setDownload(false);
//        get information from parse
            ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
            userQuery.findInBackground(
                    new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> list, ParseException e) {
                            if (e == null) {
                                for (ParseUser user : list) {
                                    CommonData.getInstance().getUsers().add(
                                            new User(user.getObjectId(),
                                                    user.getUsername(),
                                                    user.getString("name"),
                                                    user.getEmail(),
                                                    user.getBoolean("online"),
                                                    new GeoPoint(
                                                            user.getParseGeoPoint(
                                                                    "location").getLatitude(),
                                                            user.getParseGeoPoint(
                                                                    "location").getLongitude()),
                                                    user.getParseFile("userphoto")));
                                }
                                CommonData.getInstance().setDownload(true);
                                update();
                            }
                        }
                    }
            );
            firstRun = false;
            SystemClock.sleep(60000);
        }
    }

    //    function update for call from interface
    public void update() {
        if (this.activity != null) {
            activity.updateActivity(CommonData.getInstance().isDownload());
        }
    }
}
