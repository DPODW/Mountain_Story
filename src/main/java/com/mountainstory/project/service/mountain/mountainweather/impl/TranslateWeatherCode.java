package com.mountainstory.project.service.mountain.mountainweather.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TranslateWeatherCode {

    //TODO: 추후 커스텀 예외를 구현해서 -> return null 을 예외로 변경
    //5xx 통합 예외로 걍 처리
    protected String skyCode(String skyState){
        switch (skyState){
            case "1":
                return "맑음";
            case "3":
                return "구름 많음";
            case "4":
                return "흐림";
        }
        throw new RuntimeException("정의되지 않은 sky code ");
    }

    protected String rainFormCode(String rainForm){
        switch (rainForm){
            case "0":
                return "X";
                
            case "1":
                return "비";
                
            case "2":
                return "비/눈";
                
            case "3":
                return "눈";
                
            case "4":
                return "소나기";
        }
        throw new RuntimeException("정의되지 않은 rainForm Code ");
    }

    protected String windSpeedCode(double windSpeedCode){

       if(windSpeedCode<4){
           return "약함";
       }
       if(windSpeedCode>=4 && windSpeedCode<9){
           return "약간 강함";
       }
       if(windSpeedCode>=9 && windSpeedCode<14){
            return "강함";
       }
       if(windSpeedCode>14){
           return "매우 강함";
       }
        throw new RuntimeException("정의되지 않은 windSpeedCode");
    }


    protected String convertUltraMicroDust(Integer dust){
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
        throw new RuntimeException("convertUltraMicroDust 예외발생");
    }

    protected String convertMicroDust(Integer dust){
        if (dust <= 30){
            return "좋음";
        }
        if (dust >= 31 && dust <=80){
            return "보통";
        }
        if (dust >= 81 && dust <=150){
            return "나쁨";
        }
        if (dust >= 151){
            return "매우 나쁨";
        }
        throw new RuntimeException("convertMicroDust 예외");
    }

}
