package com.mountainstory.project.service.mountain.mountainweather;

import com.mountainstory.project.dto.mountain.mountainWeather.DustInfo;
import com.mountainstory.project.dto.mountain.mountainWeather.MountainWeather;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface MountainWeatherService {

    //TODO: 추후에 두 메소드를 순차적으로 호출하는 get~ 메소드 구현 필요 ex)mountainInfo 의 getAllMountainInfo 와 같은 메소드
    MountainWeather searchMountainWeather(Integer nx , Integer ny) throws ParseException;

    DustInfo searchMicroDust(String mountainLocation) throws UnsupportedEncodingException, ParseException;
}
