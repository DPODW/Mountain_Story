package com.mountainstory.project.controller;

import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.OAuthMemberSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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
    public String homePage(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null && session.getAttribute("Member")!=null){
            OAuthMemberSession sessionUser = (OAuthMemberSession) session.getAttribute("Member");
            log.info("{}",sessionUser.getEmail());
            log.info("{}",sessionUser.getName());
        }else if(session==null){
            log.info("세션 없음");
        }
        return "main/Home";
    }


    @GetMapping("/result")
    public String resultPage(){
        return "main/SearchResult";
    }


}
