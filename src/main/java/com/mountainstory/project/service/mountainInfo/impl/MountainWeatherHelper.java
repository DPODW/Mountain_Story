package com.mountainstory.project.service.mountainInfo.impl;

import com.mountainstory.project.dto.mountain.mountaininfo.MountainWeather;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MountainWeatherHelper {

    protected String baseDate(){
        LocalDateTime nowBaseDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return nowBaseDate.format(formatter);
    }

    protected String bastTime(){
        LocalDateTime time = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formattedTime = LocalDateTime.parse(time.format(formatter), formatter);

        int hour = formattedTime.getHour();
        int minute = formattedTime.getMinute();
        String baseTime = null;

        if(hour>2 && minute>=10 || hour<5 && minute<=10){
            baseTime = "0200";
        }

        if(hour>5 && minute>=10 || hour<8 && minute<=10){
            baseTime = "0500";
        }

        if(hour>8 && minute>=10 || hour<11 && minute<=10){
            baseTime = "0800";
        }

        if(hour>11 && minute>=10 || hour<14 && minute<=10){
            baseTime = "1100";
        }

        if(hour>14 && minute>=10 || hour<17 && minute<=10){
            baseTime = "1400";
        }

        if(hour>17 && minute>=10 || hour<20 && minute<=10){
            baseTime = "1700";
        }

        if(hour>20 && minute>=10 || hour<23 && minute<=10){
            baseTime = "2000";
        }

        if(hour>23 && minute>=10 || hour<02 && minute<=10){
            baseTime = "2300";
        }
        return baseTime;
    }


    protected MountainWeather getWeatherInfo(String jsonWeatherData) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        List<HashMap<String, Object>> weatherMapList = new ArrayList<>();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonWeatherData);
        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONObject jsonItems = (JSONObject) jsonBody.get("items");
        JSONArray jsonItem = (JSONArray) jsonItems.get("item");

        jsonItem.forEach(item -> {
            JSONObject weatherInfo = (JSONObject) item;
            String category = (String) weatherInfo.get("category");
            Object fcstValue = weatherInfo.get("fcstValue");

            HashMap<String,Object> weatherMap = new HashMap<>();
            weatherMap.put(category,fcstValue);

            weatherMapList.add(weatherMap);
        });

        return setMountainWeatherToDto(weatherMapList);
    }

    protected MountainWeather setMountainWeatherToDto(List<HashMap<String, Object>> weatherMapList) {
        MountainWeather mountainWeather = new MountainWeather();

        for (int i = 0; i < weatherMapList.size(); i++) {
            Map<String, Object> weatherMap = weatherMapList.get(i);

            switch (i) {
                case 0:
                    mountainWeather.setCurrentTemperature(Double.parseDouble(weatherMap.get("TMP").toString()));
                    break;
                case 1:
                    mountainWeather.setEastAndWestWindSpeed(Double.parseDouble(weatherMap.get("UUU").toString()));
                    break;
                case 2:
                    mountainWeather.setSouthAndNorthWindSpeed(Double.parseDouble(weatherMap.get("VVV").toString()));
                    break;
                case 3:
                    mountainWeather.setWindDirection(Double.parseDouble(weatherMap.get("VEC").toString()));
                    break;
                case 4:
                    mountainWeather.setAverageWindSpeed(Double.parseDouble(weatherMap.get("WSD").toString()));
                    break;
                case 5:
                    mountainWeather.setSkyState(Double.parseDouble(weatherMap.get("SKY").toString()));
                    break;
                case 6:
                    mountainWeather.setRainForm(Double.parseDouble(weatherMap.get("PTY").toString()));
                    break;
                case 7:
                    mountainWeather.setRainPercentage(Double.parseDouble(weatherMap.get("POP").toString()));
                    break;
                case 8:
                    mountainWeather.setWaveHeight(Double.parseDouble(weatherMap.get("WAV").toString()));
                    break;
                case 9:
                    mountainWeather.setRainAmount(weatherMap.get("PCP").toString());
                    break;
                case 10:
                    mountainWeather.setHumidity(Double.parseDouble(weatherMap.get("REH").toString()));
                    break;
                case 11:
                    mountainWeather.setSnowAmount(weatherMap.get("SNO").toString());
                    break;
            }
        }
        return mountainWeather;
    }

}
