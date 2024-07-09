package com.mountainstory.project.service.mountain.mountainweather;

import com.mountainstory.project.dto.mountain.mountainWeather.DustInfo;
import com.mountainstory.project.dto.mountain.mountainWeather.HikingAdvice;
import com.mountainstory.project.dto.mountain.mountainWeather.MountainWeather;
import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;

@Service
public interface MountainWeatherService {
    MountainWeather getMountainWeather(MountainCoordinate mountainCoordinate,String mountainLocation) throws ParseException, UnsupportedEncodingException;

    MountainWeather searchMountainWeather(MountainCoordinate mountainCoordinate) throws ParseException;

    DustInfo searchMicroDust(String mountainLocation) throws UnsupportedEncodingException, ParseException;

    HikingAdvice createHikingAdvice(MountainWeather mountainWeather);
}


