package com.mountainstory.project.controller;
import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.service.review.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequestMapping("/home")
@Controller
public class HomeController {
    private final OAuthMemberService oAuthMemberService;
    private final ReviewService reviewService;
    private final ReviewRankingHelper reviewRankingHelper;

    public HomeController(OAuthMemberService oAuthMemberService, ReviewService reviewService, ReviewRankingHelper reviewRankingHelper) {
        this.oAuthMemberService = oAuthMemberService;
        this.reviewService = reviewService;
        this.reviewRankingHelper = reviewRankingHelper;
    }


    @GetMapping("")
    public String homePage(@LoginMember OAuthMemberSession oAuthMemberSession, Model model, HttpServletRequest request){
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        reviewRankingHelper.findTop7GoodReview(model);

        model.addAttribute("loginMember",oAuthMemberSession);
        return "main/Home";
    }

    @GetMapping("/naverLogout")
    public String naverLogout(@LoginMember OAuthMemberSession oAuthMemberSession){
        oAuthMemberService.naverMemberLogout(oAuthMemberSession.getAccessToken());
        return "redirect:/logout";
    }
}
