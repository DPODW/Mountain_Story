package com.mountainstory.project.controller;


import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.controller.paging.PagingFunction;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.service.review.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/mountain/info/review")
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    private final PagingFunction pagingFunction;

    public ReviewController(ReviewService reviewService, PagingFunction pagingFunction) {
        this.reviewService = reviewService;
        this.pagingFunction = pagingFunction;
    }


    @PostMapping("")
    public String saveReview(@ModelAttribute ReviewInfo reviewInfo, @RequestParam Integer mountainIndex, @LoginMember OAuthMemberSession oAuthMemberSession){
        reviewService.createReviewInfo(reviewInfo, oAuthMemberSession);
        return "redirect:/mountain/info/search/one/"+mountainIndex;
    }


    @PostMapping("/rating/{reviewNumber}/{reviewRatingStat}")
    public ResponseEntity<String> reviewRating(@PathVariable Long reviewNumber,@PathVariable boolean reviewRatingStat,@LoginMember OAuthMemberSession oAuthMemberSession){
        reviewService.reviewRatingPlus(reviewNumber,reviewRatingStat,oAuthMemberSession);
        return ResponseEntity.ok("review done");
    }



}
