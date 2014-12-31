package com.example.bryan.ieee;


public class Event {

    String name;
    String location;
    String type;
    int month;
    int day;
    int year;
    char dayOfWeek; // U,M,T,W,R,F,S
    int startTime;
    int endTime;

    // Constructor.
    public Event(String name, String type, String location) {

        this.name = name;
        this.type = type;
        this.location = location;
    }
}
