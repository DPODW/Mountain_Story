package com.mountainstory.project.service.mountain.mountainweather.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConvertWeatherLocation {
    private static final int THREE_WORDS_LOCATION = 3;
    private static final int FOUR_WORDS_LOCATION = 4;

    protected String convertedShortLocation(String mountainLocation){
        int parentLocationIndex = mountainLocation.indexOf(' ');
        String parentLocation = mountainLocation.substring(0, parentLocationIndex).trim();

        switch (parentLocation.length()){
            case THREE_WORDS_LOCATION:
                return parentLocation.substring(0, parentLocation.length() - 1);

            case FOUR_WORDS_LOCATION:
                StringBuffer stringBuffer = new StringBuffer(parentLocation);
                stringBuffer.deleteCharAt(1);
                stringBuffer.deleteCharAt(2);
                //ex) "경상북도" 라는 단어가 들어오면 -> 1번째 인덱스 삭제(경북도) -> 2번째 인덱스 삭제(경북) = 완성
                return stringBuffer.toString();

            default:
                return parentLocation.substring(0,2);
        }

    }

}
