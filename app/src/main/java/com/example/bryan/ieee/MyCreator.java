package com.example.bryan.ieee;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.bryan.ieee.Event;

public class MyCreator implements Parcelable.Creator<Event> {

    public Event createFromParcel(Parcel source) {
        return new Event(source);
    }
    public Event[] newArray(int size) {
        return new Event[size];
    }
}