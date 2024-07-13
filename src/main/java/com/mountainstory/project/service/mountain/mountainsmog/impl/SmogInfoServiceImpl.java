package com.mountainstory.project.service.mountain.mountainsmog.impl;

import com.mountainstory.project.service.mountain.mountainsmog.SmogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SmogInfoServiceImpl implements SmogInfoService {

    @Value("${openapi.serviceKey}")
    private String openApiServiceKey;

    private static final int EXIST_CHILD_DETAIL2 = 2;

    @Override
    public List<String> searchTMLocation(String mountainLocation) throws UnsupportedEncodingException, ParseException {
        String encodeMountainLocation = URLEncoder.encode(convertedApiLocation(mountainLocation), "UTF-8");
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/B552584/MsrstnInfoInqireSvc/getTMStdrCrdnt")
                .queryParam("serviceKey",openApiServiceKey)
                .queryParam("returnType","JSON")
                .queryParam("numOfRows","1")
                .queryParam("pageNo","1")
                .queryParam("umdName",encodeMountainLocation)
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        String smogData = restTemplate.getForObject(uri, String.class);
        return getTMLocation(smogData);
    }


    //미세먼지 농도를 관제센터 기준으로 찾는 API 또한 있다. 현재는 사용 x
    @Override
    public String searchMeasuringStation(String tmX, String tmY) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr/B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList")
                .queryParam("serviceKey",openApiServiceKey)
                .queryParam("returnType","JSON")
                .queryParam("tmX",tmX)
                .queryParam("tmY",tmY)
                .queryParam("ver","1.1")
                .build(true)
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
        return forEntity.getBody();
    }



    private static List<String> getTMLocation(String smogData) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        List<String> TMLocation = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(smogData);
        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONArray jsonItems = (JSONArray) jsonBody.get("items");

        jsonItems.forEach(items -> {
            JSONObject smogInfo = (JSONObject) items;

            TMLocation.add((String) smogInfo.get("tmX"));
            TMLocation.add((String) smogInfo.get("tmY"));
        });
        return TMLocation;
    }

    private String convertedApiLocation(String mountainLocation){
        int parentLocationIndex = mountainLocation.indexOf(" ");
        String deletePatentLocation = mountainLocation.trim().substring(parentLocationIndex + 1);

        long checkChildDetail2 = deletePatentLocation.trim().chars().filter(i -> String.valueOf((char)i).equals(" ")).count();

        if(checkChildDetail2==EXIST_CHILD_DETAIL2){
            int childDetail2LocationIndex = deletePatentLocation.lastIndexOf(" ");
            return  deletePatentLocation.trim().substring(0, childDetail2LocationIndex);
        }
        return deletePatentLocation;
    }
}
