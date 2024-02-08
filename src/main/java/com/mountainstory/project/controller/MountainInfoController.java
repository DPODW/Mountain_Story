package com.mountainstory.project.controller;

import com.mountainstory.project.dto.mountain.mountainImg.MountainImgDto;
import com.mountainstory.project.dto.mountain.mountainInfo.MountainInfoDto;
import com.mountainstory.project.service.mountainInfo.MountainInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class MountainInfoController {

    private final MountainInfoService mountainInfoService;

    public MountainInfoController(MountainInfoService mountainInfoService) {
        this.mountainInfoService = mountainInfoService;
    }


    @GetMapping("/mountain/info/search")
    public String searchMountainInfo(@RequestParam String mountainName) throws UnsupportedEncodingException {
        mountainInfoService.getAllMountainInfo(mountainName);
        return "redirect:/home/result";
    }
}
