package com.mountainstory.project.service.mountain.mountainweather.impl;

import com.mountainstory.project.dto.mountain.mountainWeather.DustInfo;
import com.mountainstory.project.dto.mountain.mountainWeather.MountainWeather;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WeatherJsonToDto {

    private final TranslateWeatherCode translateWeatherCode;

    public WeatherJsonToDto(TranslateWeatherCode translateWeatherCode) {
        this.translateWeatherCode = translateWeatherCode;
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
                    String windSpeed = translateWeatherCode.windSpeedCode(Double.parseDouble(weatherMap.get("WSD").toString()));
                    mountainWeather.setAverageWindSpeed(windSpeed);
                    break;
                case 5:
                    mountainWeather.setSkyState(translateWeatherCode.skyCode(weatherMap.get("SKY").toString()));
                    break;
                case 6:
                    mountainWeather.setRainForm(translateWeatherCode.rainFormCode(weatherMap.get("PTY").toString()));
                    break;
                case 7:
                    mountainWeather.setRainPercentage(Integer.parseInt(weatherMap.get("POP").toString()));
                    break;
                case 8:
                    mountainWeather.setWaveHeight(Double.parseDouble(weatherMap.get("WAV").toString()));
                    break;
                case 9:
                    mountainWeather.setRainAmount(weatherMap.get("PCP").toString());
                    break;
                case 10:
                    mountainWeather.setHumidity(Integer.parseInt(weatherMap.get("REH").toString()));
                    break;
                case 11:
                    mountainWeather.setSnowAmount(weatherMap.get("SNO").toString());
                    break;
            }
        }
        return mountainWeather;
    }

    protected DustInfo getDustInfo(String jsonWeatherPollutionInfo) throws ParseException {
        DustInfo dustInfo = new DustInfo();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonWeatherPollutionInfo);
        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONArray jsonItems = (JSONArray) jsonBody.get("items");

        jsonItems.forEach(items -> {
            JSONObject pollutionInfo = (JSONObject) items;
            int microDustConcentration = Integer.parseInt(pollutionInfo.get("pm10Value").toString());
            int ultrafDustConcentration = Integer.parseInt(pollutionInfo.get("pm25Value").toString());

            dustInfo.setMicroDustConcentration(microDustConcentration);
            dustInfo.setUltrafDustConcentration(ultrafDustConcentration);

            String microDust = translateWeatherCode.convertDustState(microDustConcentration);
            String ultrafineDust = translateWeatherCode.convertDustState(ultrafDustConcentration);

            dustInfo.setMicroDustState(microDust);
            dustInfo.setUltrafineDustState(ultrafineDust);
        });
        return dustInfo;
    }
}
