package com.kjh85skill12.holyland;

public class MessageItem {

    String name;
    String message;
    String time;
    String profileUrl;
    long timeMillis;

    public MessageItem(String name, String message, String time, String profileUrl,long timeMillis) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.profileUrl = profileUrl;
        this.timeMillis = timeMillis;
    }

    //firebase DB에 저장하려면 빈 생성자가 있어야함.
    public MessageItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public long getTimeMillis() {
        return timeMillis;
    }
}
