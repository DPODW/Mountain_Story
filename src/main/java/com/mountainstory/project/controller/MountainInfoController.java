package com.mountainstory.project.controller;


import com.mountainstory.project.dto.mountain.mountainWeather.DustInfo;
import com.mountainstory.project.dto.mountain.mountainWeather.MountainWeather;
import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.service.mountain.mountaininfo.MountainInfoService;
import com.mountainstory.project.service.mountain.mountainweather.MountainWeatherService;
import com.mountainstory.project.service.mountain.mountainsmog.SmogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;


import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Controller
public class MountainInfoController {

    private final MountainInfoService mountainInfoService;

    private final MountainWeatherService mountainWeatherService;
    private final SmogInfoService smogInfoService;

    public MountainInfoController(MountainInfoService mountainInfoService, MountainWeatherService mountainWeatherService, SmogInfoService smogInfoService) {
        this.mountainInfoService = mountainInfoService;
        this.mountainWeatherService = mountainWeatherService;
        this.smogInfoService = smogInfoService;
    }


    @GetMapping("/mountain/info/search")
    public String searchMountainInfo(@RequestParam(name = "mountainName") String mountainName, Model model) throws UnsupportedEncodingException, ParseException {
        List<MountainInfoDto> allMountainInfo = mountainInfoService.getAllMountainInfo(mountainName);
        log.info("산 정보 > {}",allMountainInfo);

        MountainCoordinate mountainCoordinate = allMountainInfo.get(0).getMountainCoordinate();
        MountainWeather mountainWeather = mountainWeatherService.searchMountainWeather(mountainCoordinate.getNx(), mountainCoordinate.getNy());
        DustInfo dustInfo = mountainWeatherService.searchMicroDust(allMountainInfo.get(0).getMountainLocation());

        mountainWeather.setDustInfo(dustInfo);
        log.info("산 날씨 > {}",mountainWeather);

        model.addAttribute("mountainWeather",mountainWeather);
        return "main/SearchResult";
    }

}
