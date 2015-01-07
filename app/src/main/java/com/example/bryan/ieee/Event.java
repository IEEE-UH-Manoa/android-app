package com.example.bryan.ieee;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;


public class Event implements Parcelable {

    private static final int TBD = 0;

    private String name;
    private String location;
    private String type;
    private String date;
    private int month;
    private int day;
    private int year;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String description;


    // Constructor.
    public Event(String name, String date, String startTime, String endTime, String location, String type, String description) {

        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.type = type;
        this.description = description;

        setDate(date);
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return "Description:\n\n" + description;
    }

    /**
     * This will be used only by the com.example.bryan.ieee.MyCreator
     * @param source
     */
    public Event(Parcel source){
            /*
             * Reconstruct from the Parcel
             */
        name = source.readString();
        date = source.readString();
        startTime = source.readString();
        endTime = source.readString();
        location = source.readString();
        type = source.readString();
        description = source.readString();
        month = source.readInt();
        day = source.readInt();
        dayOfWeek = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(location);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeString(dayOfWeek);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    // set month, day, year, and day of the week
    private void setDate(String date) {

        if(!date.equals("TBD")) {
            String[] separated = date.split("/");
            month = Integer.parseInt(separated[0]);
            day = Integer.parseInt(separated[1]);
            year = Integer.parseInt(separated[2]);

            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            int dayOfWeekNum = c.get(Calendar.DAY_OF_WEEK);


            Log.d("events", String.valueOf(dayOfWeekNum));


            setDayOfWeekString(dayOfWeekNum);
        }

        else {
            month = day = year = TBD;
            dayOfWeek = "";
        }

    }

    private void setDayOfWeekString(int num) {
        switch(num) {
            case 1:
                dayOfWeek = "Sun";
                break;
            case 2:
                dayOfWeek = "Mon";
                break;
            case 3:
                dayOfWeek = "Tue";
                break;
            case 4:
                dayOfWeek = "Wed";
                break;
            case 5:
                dayOfWeek = "Thu";
                break;
            case 6:
                dayOfWeek = "Fri";
                break;
            case 7:
                dayOfWeek = "Sat";
                break;
            default:
                dayOfWeek = "";
        }
    }

    public String setEventTime() {
        if(startTime.equals(""))
            return "Time: TBD";

        String eventTime = startTime + " - " + endTime;

        return eventTime;
    }

    public String monthToString() {
        switch(month) {
            case 1: return "JAN";
            case 2: return "FEB";
            case 3: return "MAR";
            case 4: return "APR";
            case 5: return "MAY";
            case 6: return "JUN";
            case 7: return "JUL";
            case 8: return "AUG";
            case 9: return "SEP";
            case 10: return "OCT";
            case 11: return "NOV";
            case 12: return "DEC";
            default: return "TBD";
        }
    }

    public String dayToString() {
        if(day == 0)
            return "";

        else
            return String.valueOf(day);
    }

    public String locationToString() {
        if(location.equals("") || location.equals("TBD"))
            return "Location: TBD";

        else
            return location;
    }

}


