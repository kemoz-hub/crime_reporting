package com.example.crimereporting;

public class citizensnotifications {
    String notification;
    String name;
    public citizensnotifications() {
    }

    public citizensnotifications(String notification,String name) {
        this.notification = notification;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
