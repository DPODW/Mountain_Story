package com.mountainstory.project.dto.mountain.mountaininfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class MountainWeather {

    //TMP (1시간 기온)
    private double currentTemperature;

    //UUU
    private double eastAndWestWindSpeed;

    //VVV
    private double southAndNorthWindSpeed;

    //WSD
    private double averageWindSpeed;

    //VEC
    private double windDirection;

    //SKY
    private double skyState;

    //PTY
    private double rainForm;

    //POP (1시간 강수량)
    private double rainPercentage;

    //PCP
    private String rainAmount;

    //REH
    private double humidity;

    //WAV
    private double waveHeight;

    //SNO (1시간 신적설)
    private String snowAmount;

    public MountainWeather(double currentTemperature, double eastAndWestWindSpeed, double southAndNorthWindSpeed, double averageWindSpeed, double windDirection, double skyState, double rainForm,
                           double rainPercentage, String rainAmount, double humidity, double waveHeight, String snowAmount) {
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
    }
}
