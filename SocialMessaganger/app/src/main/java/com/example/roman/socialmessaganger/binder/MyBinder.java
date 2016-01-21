package com.example.roman.socialmessaganger.binder;

import android.os.Binder;

import com.example.roman.socialmessaganger.service.MyService;

public class MyBinder extends Binder {

    private MyService service;

    public MyBinder(MyService service) {
        this.service = service;
    }

    public MyService getService() {
        return service;
    }

    public void setService(MyService service) {
        this.service = service;
    }
}
