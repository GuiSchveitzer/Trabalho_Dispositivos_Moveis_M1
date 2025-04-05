package com.example.dynamicworklog.util;

import java.util.ArrayList;

public class TimeHour {
    private static ArrayList<String> timeWorked = new ArrayList<String>();
    private static int hour, minute, second, temp;

    public static ArrayList<String> getTimeWorked() {
        return timeWorked;
    }

    public static void setTimeWorked(ArrayList<String> timeWorked) {
        TimeHour.timeWorked = timeWorked;
    }

    public static void addTime(int hour, int minute, int second){
        String hourBefore = hour + ":" + "00:00";
        String hourAfter = (hour+1) + ":" + "00:00";
        getTimeWorked().add(hourBefore+"-"+hourAfter);
    }

    public static void resetTime(){
        getTimeWorked().clear();
    }

    public static void compareTime(int hour, int minute, int second){
        //remove last time
        if(hour < getHour()){
            for(int i = hour; i < getHour(); i++){
                getTimeWorked().remove((getTimeWorked().size()-1));
            }
        }

        if((hour >= 8 && hour <= 17) && hour != 12){

            for(int i = hour; i > getHour(); i--){
                addTime( temp,  minute,  second);
                temp++;
            }
            setHour(temp);
        }else if(hour > 0){
            resetTime();
            setHour(0);
        }


    }

    public static int getHour() {
        return hour;
    }

    public static void setHour(int hour) {
        TimeHour.hour = hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static void setMinute(int minute) {
        TimeHour.minute = minute;
    }

    public static int getSecond() {
        return second;
    }

    public static void setSecond(int second) {
        TimeHour.second = second;
    }
}
