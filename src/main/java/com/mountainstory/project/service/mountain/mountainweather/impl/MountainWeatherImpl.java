package com.mountainstory.project.service.mountain.mountainweather.impl;

import com.mountainstory.project.dto.mountain.mountainWeather.DustInfo;
import com.mountainstory.project.dto.mountain.mountainWeather.HikingAdvice;
import com.mountainstory.project.dto.mountain.mountainWeather.MountainWeather;
import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.service.mountain.mountainweather.MountainWeatherService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Service
public class MountainWeatherImpl implements MountainWeatherService {

    private static final int PARENT_LOCATION_LIST_INDEX = 1;

    private static final int CHILD_LOCATION_LIST_INDEX = 0;

    private final ConvertWeatherLocation convertWeatherLocation;

    private final WeatherJsonToDto weatherJsonToDto;

    private final WeatherSearchDate weatherSearchDate;
    @Value("${openapi.serviceKey}")
    private String openApiServiceKey;

    public MountainWeatherImpl(ConvertWeatherLocation convertWeatherLocation, WeatherJsonToDto weatherJsonToDto, WeatherSearchDate weatherSearchDate) {
        this.convertWeatherLocation = convertWeatherLocation;
        this.weatherJsonToDto = weatherJsonToDto;
        this.weatherSearchDate = weatherSearchDate;
    }

    @Override
    public MountainWeather getMountainWeather(MountainCoordinate mountainCoordinate, String mountainLocation) throws ParseException, UnsupportedEncodingException {
        MountainWeather mountainWeather = searchMountainWeather(mountainCoordinate);
        DustInfo dustInfo = searchMicroDust(mountainLocation);
        mountainWeather.setDustInfo(dustInfo);
        mountainWeather.setHikingAdvice(createHikingAdvice(mountainWeather));
        return mountainWeather;
    }

    @Override
    public MountainWeather searchMountainWeather(MountainCoordinate mountainCoordinate) throws ParseException {
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                .queryParam("serviceKey",openApiServiceKey)
                .queryParam("numOfRows","12")
                .queryParam("pageNo","1")
                .queryParam("dataType","JSON")
                .queryParam("base_date",weatherSearchDate.baseDate())
                .queryParam("base_time",weatherSearchDate.bastTime())
                .queryParam("nx",mountainCoordinate.getNy().toString())
                .queryParam("ny",mountainCoordinate.getNy().toString())
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        String jsonWeatherData = restTemplate.getForObject(uri, String.class);
        return weatherJsonToDto.getWeatherInfo(jsonWeatherData);
    }

    @Override
    public DustInfo searchMicroDust(String mountainLocation) throws UnsupportedEncodingException, ParseException {
        List<String> mountainLocationList = convertWeatherLocation.convertedShortLocation(mountainLocation);
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst")
                .queryParam("serviceKey",openApiServiceKey)
                .queryParam("returnType","JSON")
                .queryParam("numOfRows","50")
                .queryParam("pageNo","1")
                .queryParam("sidoName",mountainLocationList.get(PARENT_LOCATION_LIST_INDEX))
                .queryParam("searchCondition","HOUR")
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        String jsonWeatherPollutionInfo = restTemplate.getForObject(uri, String.class);
        return weatherJsonToDto.getDustInfo(jsonWeatherPollutionInfo,mountainLocationList.get(CHILD_LOCATION_LIST_INDEX));
    }

    @Override
    public HikingAdvice createHikingAdvice(MountainWeather mountainWeather) {
        HikingAdvice hikingAdvice = new HikingAdvice();

        switch (mountainWeather.getSkyState()){
            case "맑음":
                hikingAdvice.setTodaySky("sunnyView");
                break;

            case "흐림":
                hikingAdvice.setTodaySky("blurView");
                break;

            case "구름 많음":
                hikingAdvice.setTodaySky("cloudView");
                break;
        }


        if(mountainWeather.getCurrentTemperature() <= 10){
            hikingAdvice.setTodayCloths("winterClothes");
        } else if (mountainWeather.getCurrentTemperature() >= 11 || mountainWeather.getCurrentTemperature() <= 20) {
            hikingAdvice.setTodayCloths("springAndFallClothes");
        } else {
            hikingAdvice.setTodayCloths("summerClothes");
        }

        log.info("현재 비 폼 >>{}",mountainWeather.getRainForm());
        switch (mountainWeather.getRainForm()){

            case "X":
                hikingAdvice.setTodayRainOrSnow("X");
                break;

            case "비", "소나기":
                hikingAdvice.setTodayRainOrSnow("rainDay");
                break;

            case "비/눈":
                hikingAdvice.setTodayRainOrSnow("rainAndSnowDay");
                break;

            case "눈":
                hikingAdvice.setTodayRainOrSnow("snowDay");
                break;
        }



        switch (mountainWeather.getAverageWindSpeed()){
            case "약함":
                hikingAdvice.setTodayWindSpeed("weakWind");
                break;

            case "약간 강함":
                hikingAdvice.setTodayWindSpeed("littleStrongWind");
                break;

            case "강함":
                hikingAdvice.setTodayWindSpeed("strongWind");
                break;

            case "매우 강함":
                hikingAdvice.setTodayWindSpeed("veryStrongWind");
                break;
        }

        if (mountainWeather.getDustInfo().getMicroDustConcentration()>=81 || mountainWeather.getDustInfo().getUltrafDustConcentration()>=36){
            hikingAdvice.setTodayDustInfo("DustHigh");
        }
        return hikingAdvice;
    }

}