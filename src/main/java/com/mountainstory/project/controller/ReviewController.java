package com.mountainstory.project.controller;


import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.config.exception.AjaxException;
import com.mountainstory.project.controller.paging.PagingFunction;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.service.review.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/mountain/info/review")
@Controller
public class ReviewController {

    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("")
    public String saveReview(@ModelAttribute ReviewInfo reviewInfo, @RequestParam Integer mountainIndex,
                             @LoginMember OAuthMemberSession oAuthMemberSession){
        reviewService.createReviewInfo(reviewInfo, oAuthMemberSession);
        return "redirect:/mountain/info/search/one/"+mountainIndex;
    }

    @PostMapping("/rating/{reviewNumber}/{reviewRatingStat}")
    public ResponseEntity<String> reviewRating(@PathVariable Long reviewNumber,@PathVariable boolean reviewRatingStat,@LoginMember OAuthMemberSession oAuthMemberSession){
        reviewService.reviewRatingPlus(reviewNumber,reviewRatingStat,oAuthMemberSession);
        return ResponseEntity.ok("review done");
    }

    @PostMapping("/check/content")
    public ResponseEntity<String> reviewContentCheck(@RequestParam String reviewTitle,@RequestParam String reviewContent){
        if((reviewTitle.length()<4 || reviewTitle.length()>12) || (reviewContent.length()<15 || reviewContent.length()>100)){
           throw new AjaxException("리뷰 작성 규칙 틀림");
        }
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/delete/{reviewNumber}")
    public ResponseEntity<String> reviewDelete(@PathVariable Long reviewNumber){
        reviewService.deleteReviewById(reviewNumber);
        return ResponseEntity.ok("ok");
        //리뷰가 삭제되면 리뷰 평가 기록도 전부 제거되어야 한다.
    }


}
