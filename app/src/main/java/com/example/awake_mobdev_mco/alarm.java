package com.example.awake_mobdev_mco;

import java.util.ArrayList;

public class alarm {
    
    private String time;
    private ArrayList<String> days;
    private String sound;

    private boolean enabled;
    private String dayPart; //AM OR PM (this is the closest thing i saw lang lol)

    public alarm(String time, ArrayList<String> days, String dayPart) {
        this.time = time;
        this.days = days;
        this.dayPart = dayPart;
    }

    public void setEnabled(boolean enabled) {
        enabled = enabled;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDayPart(String dayPart) {
        this.dayPart = dayPart;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public String getSound() {
        return sound;
    }

    public String getTime() {
        return time;
    }

    public String getDayPart() {
        return dayPart;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
