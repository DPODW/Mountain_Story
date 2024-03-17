package com.mountainstory.project.dto.mountain.mountainWeather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class HikingAdvice {

    private String todaySky;

    private String todayCloths;

    private String todayRainOrSnow;

    private String todayWindSpeed;

    private String todayDustInfo;

    public HikingAdvice(String todaySky, String todayCloths, String todayRainOrSnow, String todayWindSpeed, String todayDustInfo) {
        this.todaySky = todaySky;
        this.todayCloths = todayCloths;
        this.todayRainOrSnow = todayRainOrSnow;
        this.todayWindSpeed = todayWindSpeed;
        this.todayDustInfo = todayDustInfo;
    }
}
