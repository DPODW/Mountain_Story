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
    private String ultraMicroDustState;
    private Integer microDustConcentration;
    private Integer ultrafDustConcentration;

    public DustInfo(String microDustState, String ultraMicroDustState, Integer microDustConcentration, Integer ultrafDustConcentration) {
        this.microDustState = microDustState;
        this.ultraMicroDustState = ultraMicroDustState;
        this.microDustConcentration = microDustConcentration;
        this.ultrafDustConcentration = ultrafDustConcentration;
    }
}
