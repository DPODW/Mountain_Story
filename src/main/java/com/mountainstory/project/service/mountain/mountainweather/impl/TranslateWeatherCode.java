package com.mountainstory.project.service.mountain.mountainweather.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TranslateWeatherCode {

    //TODO: 변수 재할당 -> 그냥 문자 그대로 return 방식으로 변경 (최종 return 은 null)
    protected String skyCode(String skyState){
        String translateSkyStateCode = null;

        switch (skyState){
            case "1":
                translateSkyStateCode = "맑음";
                break;
            case "3":
                translateSkyStateCode = "구름 많음";
                break;
            case "4":
                translateSkyStateCode = "흐림";
                break;
        }
        return translateSkyStateCode;
    }

    protected String rainFormCode(String rainForm){
        String translateRainFormCode = null;

        switch (rainForm){
            case "0":
                translateRainFormCode = "X";
                break;
            case "1":
                translateRainFormCode = "비";
                break;
            case "2":
                translateRainFormCode = "비/눈";
                break;
            case "3":
                translateRainFormCode = "눈";
                break;
            case "4":
                translateRainFormCode = "소나기";
                break;
        }
        return translateRainFormCode;
    }

    protected String windSpeedCode(double windSpeedCode){
        String translateSpeedCode = null;

       if(windSpeedCode<4){
           translateSpeedCode = "약함";
       }
       if(windSpeedCode>=4 && windSpeedCode<9){
           translateSpeedCode = "약간 강함";
       }
       if(windSpeedCode>=9 && windSpeedCode<14){
            translateSpeedCode = "강함";
       }
       if(windSpeedCode>14){
           translateSpeedCode = "매우 강함";
       }
       return translateSpeedCode;
    }


    protected String convertDustState(Integer dust){
        if (dust <= 15){
            return "좋음";
        }
        if (dust >= 16 && dust <=35){
            return "보통";
        }
        if (dust >= 36 && dust <=75){
            return "나쁨";
        }
        if (dust >= 76){
            return "매우 나쁨";
        }
        return null;
    }

}
