package com.mountainstory.project.controller;


import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.service.review.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping("/mountain/info/review")
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("")
    public String saveReview(@ModelAttribute ReviewInfo reviewInfo, @RequestParam Integer mountainIndex, @LoginMember OAuthMemberSession oAuthMemberSession){
        reviewService.createReviewInfo(reviewInfo, oAuthMemberSession);
        return "redirect:/mountain/info/search/one/"+mountainIndex;
    }


    @PostMapping("/rating/{reviewNumber}/{reviewRatingStat}")
    public ResponseEntity<String> reviewRating(@PathVariable Long reviewNumber,@PathVariable String reviewRatingStat,@LoginMember OAuthMemberSession oAuthMemberSession){
        reviewService.reviewRatingPlus(reviewNumber,reviewRatingStat,oAuthMemberSession);
        return ResponseEntity.ok("review done");
    }

}
