package com.mountainstory.project.controller;

import com.mountainstory.project.service.mountainInfo.MountainInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Slf4j
@Controller
public class MountainInfoController {

    private final MountainInfoService mountainInfoService;

    public MountainInfoController(MountainInfoService mountainInfoService) {
        this.mountainInfoService = mountainInfoService;
    }


    @GetMapping("/mountain/info/search")
    public String searchMountainInfo(@RequestParam String mountainName) throws UnsupportedEncodingException {
        mountainInfoService.searchMountainInfo(mountainName);
        return "redirect:/home/result";
    }
}
