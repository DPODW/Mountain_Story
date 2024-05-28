package com.mountainstory.project.service.mountain.mountainweather.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ConvertWeatherLocation {
    private static final int THREE_WORDS_LOCATION = 3;
    private static final int FOUR_WORDS_LOCATION = 4;

    protected List<String> convertedShortLocation(String mountainLocation) throws UnsupportedEncodingException {
        String mountainLocationEmptyReplace = mountainLocation.replaceAll("\\s+"," ");
        //일관성을 위해 위치 값 (ex 서울특별시 중랑구 면목동) 사이의 공백을 하나로 통일함

        int parentLocationIndex = mountainLocationEmptyReplace.indexOf(' ');
        String parentLocation = mountainLocationEmptyReplace.substring(0, parentLocationIndex).trim();

        int childLocationIndex = mountainLocationEmptyReplace.indexOf(' ',parentLocationIndex+1);
        String childLocation = mountainLocationEmptyReplace.substring(parentLocationIndex+1,childLocationIndex).trim();

        List<String> convertLocation = new ArrayList<>();
        convertLocation.add(childLocation);
        //하위 지역은 WeatherJsonToDto 에서 사용되어야 하고, 미세먼지 제공 API 가 요청하는 지역은 상위 지역이다.
        //그렇기 때문에 인코딩은 상위 지역만 이루어지면 된다. ( 하위 지역에 인코딩 할 시 WeatherJsonToDto 에서 조건 검사 오류 발생 115Line)

        switch (parentLocation.length()){
            case THREE_WORDS_LOCATION:
                convertLocation.add(URLEncoder.encode(parentLocation.substring(0, parentLocation.length() - 1), "UTF-8"));
                return convertLocation;

            case FOUR_WORDS_LOCATION:
                StringBuffer stringBuffer = new StringBuffer(parentLocation);
                stringBuffer.deleteCharAt(1);
                stringBuffer.deleteCharAt(2);
                //ex) "경상북도" 라는 단어가 들어오면 -> 1번째 인덱스 삭제(경북도) -> 2번째 인덱스 삭제(경북) = 완성

                convertLocation.add(URLEncoder.encode(stringBuffer.toString(), "UTF-8"));
                return convertLocation;

            default:
                convertLocation.add(URLEncoder.encode(parentLocation.substring(0,2), "UTF-8"));
                log.info("{}",parentLocation.substring(0,2));
                return convertLocation;
        }

    }

}