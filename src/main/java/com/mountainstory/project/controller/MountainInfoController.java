package com.mountainstory.project.controller;


import com.mountainstory.project.dto.mountain.mountainregion.MountainCoordinate;
import com.mountainstory.project.service.mountainInfo.MountainInfoService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;


import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Controller
public class MountainInfoController {

    private final MountainInfoService mountainInfoService;

    public MountainInfoController(MountainInfoService mountainInfoService) {
        this.mountainInfoService = mountainInfoService;
    }


    @GetMapping("/mountain/info/search")
    public String searchMountainInfo(@RequestParam(name = "mountainName") String mountainName) throws UnsupportedEncodingException, ParseException {
        ss();
        List<MountainInfoDto> allMountainInfo = mountainInfoService.getAllMountainInfo(mountainName);
        MountainCoordinate mountainCoordinate = allMountainInfo.get(0).getMountainCoordinate();
        mountainInfoService.searchMountainWeather(mountainCoordinate.getNx(), mountainCoordinate.getNy());

        return "redirect:/home/result";
    }

    private void ss(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String nnow = now.format(formatter);
        log.info("{}",nnow);
    }

}
