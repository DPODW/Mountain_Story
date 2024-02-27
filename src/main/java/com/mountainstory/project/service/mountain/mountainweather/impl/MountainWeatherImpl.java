package com.mountainstory.project.service.mountain.mountainweather.impl;

import com.mountainstory.project.dto.mountain.mountainWeather.DustInfo;
import com.mountainstory.project.dto.mountain.mountainWeather.MountainWeather;
import com.mountainstory.project.service.mountain.mountainweather.MountainWeatherService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@Slf4j
@Service
public class MountainWeatherImpl implements MountainWeatherService {

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
    public MountainWeather searchMountainWeather(Integer nx , Integer ny) throws ParseException {
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                .queryParam("serviceKey",openApiServiceKey)
                .queryParam("numOfRows","12")
                .queryParam("pageNo","1")
                .queryParam("dataType","JSON")
                .queryParam("base_date",weatherSearchDate.baseDate())
                .queryParam("base_time","0800")
                .queryParam("nx",nx.toString())
                .queryParam("ny",ny.toString())
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        String jsonWeatherData = restTemplate.getForObject(uri, String.class);
        return weatherJsonToDto.getWeatherInfo(jsonWeatherData);
    }

    @Override
    public DustInfo searchMicroDust(String mountainLocation) throws UnsupportedEncodingException, ParseException {
        String encodeMountainLocation = URLEncoder.encode(convertWeatherLocation.convertedShortLocation(mountainLocation), "UTF-8");
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst")
                .queryParam("serviceKey",openApiServiceKey)
                .queryParam("returnType","JSON")
                .queryParam("numOfRows","1")
                .queryParam("pageNo","1")
                .queryParam("sidoName",encodeMountainLocation)
                .queryParam("searchCondition","HOUR")
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        String jsonWeatherPollutionInfo = restTemplate.getForObject(uri, String.class);
        return weatherJsonToDto.getDustInfo(jsonWeatherPollutionInfo);
    }

}
