package com.example.roman.socialmessaganger.commondata;

import com.parse.ParseFile;

public class MyMessage {
    private String id;
    private String subject;
    private String message;
    private ParseFile file;
    private User from;
    private User to;

    public MyMessage(String id, String subject, String message,
                     ParseFile file, User from, User to) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.file = file;
        this.from = from;
        this.to = to;
    }

    public MyMessage(String id, String subject, String message, User from, User to) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParseFile getFile() {
        return file;
    }

    public void setFile(ParseFile file) {
        this.file = file;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
