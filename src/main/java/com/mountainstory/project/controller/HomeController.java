package com.mountainstory.project.controller;
import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/home")
@Controller
public class HomeController {
    private final OAuthMemberService oAuthMemberService;

    public HomeController(OAuthMemberService oAuthMemberService) {
        this.oAuthMemberService = oAuthMemberService;
    }


    @GetMapping("")
    public String homePage(@LoginMember OAuthMemberSession oAuthMemberSession, Model model){
        if(oAuthMemberSession != null && oAuthMemberSession.getType().equals("naver")){
            model.addAttribute("naver","naver");
        } else if(oAuthMemberSession != null && oAuthMemberSession.getType().equals("kakao")) {
            model.addAttribute("kakao","kakao");
        }
        model.addAttribute("loginMember",oAuthMemberSession);
        return "main/Home";
    }


    @GetMapping("/result")
    public String resultPage(){
        return "main/SearchResult";
    }


    @GetMapping("/naverLogout")
    public String naverLogout(@LoginMember OAuthMemberSession oAuthMemberSession){
        oAuthMemberService.naverMemberLogout(oAuthMemberSession.getAccessToken());
        return "redirect:/logout";
    }





}
