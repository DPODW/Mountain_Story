package com.mountainstory.project.dto.mountain.mountainWeather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class DustInfo {
    private String microDustState;
    private String ultrafineDustState;
    private Integer microDustConcentration;
    private Integer ultrafDustConcentration;

    public DustInfo(String microDustState, String ultrafineDustState, Integer microDustConcentration, Integer ultrafDustConcentration) {
        this.microDustState = microDustState;
        this.ultrafineDustState = ultrafineDustState;
        this.microDustConcentration = microDustConcentration;
        this.ultrafDustConcentration = ultrafDustConcentration;
    }
}
