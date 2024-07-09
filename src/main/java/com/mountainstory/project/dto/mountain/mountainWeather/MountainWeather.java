package com.mountainstory.project.dto.mountain.mountainWeather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class MountainWeather {

    //아래 주석 영문 3글자는 기상청 API 가 제공하는 코드임. EX) TMP == 평균 온도를 의미

    //TMP (1시간 기온)
    private double currentTemperature;

    //UUU
    private double eastAndWestWindSpeed;

    //VVV
    private double southAndNorthWindSpeed;

    //WSD
    private String averageWindSpeed;

    //VEC
    private double windDirection;

    //SKY
    private String skyState;

    //PTY
    private String rainForm;

    //POP (1시간 강수량)
    private Integer rainPercentage;

    //PCP
    private String rainAmount;

    //REH
    private Integer humidity;

    //WAV
    private double waveHeight;

    //SNO (1시간 신적설)
    private String snowAmount;

    //미세먼지 (미세 , 초미세)
    private DustInfo dustInfo;

    private HikingAdvice hikingAdvice;

    public MountainWeather(double currentTemperature, double eastAndWestWindSpeed, double southAndNorthWindSpeed, String averageWindSpeed, double windDirection, String skyState, String rainForm,
                           Integer rainPercentage, String rainAmount, Integer humidity, double waveHeight, String snowAmount,DustInfo dustInfo,HikingAdvice hikingAdvice) {
        this.currentTemperature = currentTemperature;
        this.eastAndWestWindSpeed = eastAndWestWindSpeed;
        this.southAndNorthWindSpeed = southAndNorthWindSpeed;
        this.averageWindSpeed = averageWindSpeed;
        this.windDirection = windDirection;
        this.skyState = skyState;
        this.rainForm = rainForm;
        this.rainPercentage = rainPercentage;
        this.rainAmount = rainAmount;
        this.humidity = humidity;
        this.waveHeight = waveHeight;
        this.snowAmount = snowAmount;
        this.dustInfo=dustInfo;
        this.hikingAdvice=hikingAdvice;
    }
}
