package com.mountainstory.project.controller;
import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
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
