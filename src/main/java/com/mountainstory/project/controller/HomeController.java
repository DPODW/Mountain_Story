package com.mountainstory.project.controller;
import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.service.review.ReviewService;
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

    public HomeController(OAuthMemberService oAuthMemberService, ReviewService reviewService) {
        this.oAuthMemberService = oAuthMemberService;
        this.reviewService = reviewService;
    }


    @GetMapping("")
    public String homePage(@LoginMember OAuthMemberSession oAuthMemberSession, Model model){
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        model.addAttribute("loginMember",oAuthMemberSession);

        List<ReviewInfo> top3GoodReview = reviewService.findTop3GoodReview();
        log.info("추천수 많은 상위 3개 리뷰 >>{}",top3GoodReview);
        return "main/Home";
    }

    @GetMapping("/naverLogout")
    public String naverLogout(@LoginMember OAuthMemberSession oAuthMemberSession){
        oAuthMemberService.naverMemberLogout(oAuthMemberSession.getAccessToken());
        return "redirect:/logout";
    }
}
