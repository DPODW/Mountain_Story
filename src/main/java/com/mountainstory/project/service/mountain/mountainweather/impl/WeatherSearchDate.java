package com.mountainstory.project.service.mountain.mountainweather.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherSearchDate {

    protected String baseDate(){
        LocalDateTime nowBaseDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return nowBaseDate.format(formatter);
    }

    //TODO: 시간 완벽 해결 필요 (고장 남)
    public String bastTime(){
        LocalDateTime time = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formattedTime = LocalDateTime.parse(time.format(formatter), formatter);

        int hour = formattedTime.getHour();
        int minute = formattedTime.getMinute();
        String baseTime = null;

        if ((hour >= 2 && minute >= 10) && (hour < 5 || (hour == 5 && minute <= 10))) {
            baseTime = "0200";
        } else if ((hour >= 5 && minute >= 10) && (hour < 8 || (hour == 8 && minute <= 10))) {
            baseTime = "0500";
        } else if ((hour >= 8 && minute >= 10) && (hour < 11 || (hour == 11 && minute <= 10))) {
            baseTime = "0800";
        } else if ((hour >= 11 && minute >= 10) && (hour < 14 || (hour == 14 && minute <= 10))) {
            baseTime = "1100";
        } else if ((hour >= 14 && minute >= 10) && (hour < 17 || (hour == 17 && minute <= 10))) {
            baseTime = "1400";
        } else if ((hour >= 17 && minute >= 10) && (hour < 20 || (hour == 20 && minute <= 10))) {
            baseTime = "1700";
        } else if ((hour >= 20 && minute >= 10) && (hour < 23 || (hour == 23 && minute <= 10))) {
            baseTime = "2000";
        } else {
            baseTime = "2300";
        }
        return baseTime;
    }
}
