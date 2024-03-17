package com.mountainstory.project.controller;


import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.dto.mountain.mountainWeather.MountainWeather;
import com.mountainstory.project.service.mountain.mountaininfo.MountainInfoService;
import com.mountainstory.project.service.mountain.mountainweather.MountainWeatherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RequestMapping("/mountain/info/search")
@Controller
public class MountainInfoController {
    private final MountainInfoService mountainInfoService;

    private final MountainWeatherService mountainWeatherService;

    private final OAuthMemberService oAuthMemberService;

    public MountainInfoController(MountainInfoService mountainInfoService, MountainWeatherService mountainWeatherService, OAuthMemberService oAuthMemberService) {
        this.mountainInfoService = mountainInfoService;
        this.mountainWeatherService = mountainWeatherService;
        this.oAuthMemberService = oAuthMemberService;
    }

    //TODO:get~ 메소드가 존재하니까 api 들은 범위를 제한해도될듯


    @GetMapping("/one/{mountainIndex}")
    public String mountainInfoOne(@LoginMember OAuthMemberSession oAuthMemberSession,@PathVariable(name = "mountainIndex") Integer mountainIndex,HttpServletRequest request,Model model) throws UnsupportedEncodingException, ParseException {
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        HttpSession session = request.getSession(false);

        List<MountainInfoDto> allMountainInfoList = (List<MountainInfoDto>) session.getAttribute("allMountainInfoList");

        MountainInfoDto mountainInfoOne = mountainInfoService.setCoordinateInfo(allMountainInfoList.get(mountainIndex));
        MountainWeather mountainWeather = mountainWeatherService.getMountainWeather(mountainInfoOne.getMountainCoordinate(), mountainInfoOne.getMountainLocation());
        session.removeAttribute("allMountainInfoList");

        //TODO: 새로고침시 NULL 에러 해결 OR 예외 처리 필요 (list 화면으로 리다이렉트)

        log.info("검색된 최종 산 >> {}",mountainInfoOne);
        model.addAttribute("loginMember",oAuthMemberSession);
        model.addAttribute("mountainInfoOne",mountainInfoOne);
        model.addAttribute("mountainWeather",mountainWeather);
        return "main/SearchResult";
    }


    @GetMapping("/list")
    public String mountainInfoList(@LoginMember OAuthMemberSession oAuthMemberSession, @RequestParam(name = "mountainName") String mountainName, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        model.addAttribute("showLoading",true);
        List<MountainInfoDto> allMountainInfoList = mountainInfoService.getAllMountainInfoList(mountainName);

        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);

        if(allMountainInfoList.size()!=0){
            HttpSession session = request.getSession(false);
            session.setAttribute("allMountainInfoList",allMountainInfoList);
        }


        model.addAttribute("loginMember",oAuthMemberSession);
        model.addAttribute("mountainInfoList",allMountainInfoList);
        return "main/SearchResultList";
    }

}
