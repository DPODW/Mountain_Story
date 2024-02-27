package com.mountainstory.project.service.mountain.mountainsmog;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface SmogInfoService {

/** 미세먼지를 측정소 위치를 기준으로 해서 검색하기 위한 기능들
    좌표값을 구하는 searchTMLocation(메소드 가공 완료. 좌표값을 배열로 반환)
    메소드와 측정소 위치를 구하는 searchMeasuringStation 메소드가 구현되어 있음 (JSON 데이터 가공 필요).
    구현 시 -> 측정소 위치로 미세먼지를 구하는 API 만 구현하면 됌. **/

    List<String> searchTMLocation (String mountainLocation) throws UnsupportedEncodingException, ParseException;

    String searchMeasuringStation(String tmX , String tmY);


}
