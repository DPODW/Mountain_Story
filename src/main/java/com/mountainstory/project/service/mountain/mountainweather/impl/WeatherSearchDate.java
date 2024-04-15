package com.mountainstory.project.service.mountain.mountainweather.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class WeatherSearchDate {

    protected String baseDate(){
        LocalDateTime nowBaseDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDateTime localDateTime = getLocalDateTime();

        if(localDateTime.getHour()==0 && (localDateTime.getHour()<=2 || localDateTime.getMinute()<10)){
            LocalDateTime midNight = LocalDateTime.now().minusDays(1);
            return midNight.format(formatter);
        }

        return nowBaseDate.format(formatter);
    }

    protected String bastTime(){
        LocalDateTime formattedTime = getLocalDateTime();

        int hour = formattedTime.getHour();
        int minute = formattedTime.getMinute();


        if ((hour > 2 || (hour == 2 && minute >= 10)) && (hour < 5 || (hour == 5 && minute < 10))) {
            return "0200";
        }
        if ((hour > 5 || (hour == 5 && minute >= 10)) && (hour < 8 || (hour == 8 && minute < 10))) {
            return "0500";
        }
        if ((hour > 8 || (hour == 8 && minute >= 10)) && (hour < 11 || (hour == 11 && minute < 10))) {
            return "0800";
        }
        if ((hour > 11 || (hour == 11 && minute >= 10)) && (hour < 14 || (hour == 14 && minute < 10))) {
            return "1100";
        }
        if ((hour > 14 || (hour == 14 && minute >= 10)) && (hour < 17 || (hour == 17 && minute < 10))) {
            return "1400";
        }
        if ((hour > 17 || (hour == 17 && minute >= 10)) && (hour < 20 || (hour == 20 && minute < 10))) {
            return "1700";
        }
        if ((hour > 20 || (hour == 20 && minute >= 10)) && (hour < 23 || (hour == 23 && minute < 10))) {
            return "2000";
        }
        if ((hour > 23 || (hour == 23 && minute >= 10)) || (hour < 2 || (hour == 2 && minute < 10))) {
            return "2300";
        } else {
            return "Error: unknown time";
        }
    }


    private static LocalDateTime getLocalDateTime() {
        LocalDateTime time = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formattedTime = LocalDateTime.parse(time.format(formatter), formatter);
        return formattedTime;
    }
}
