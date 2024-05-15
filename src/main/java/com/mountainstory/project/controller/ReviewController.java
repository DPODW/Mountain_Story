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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    //DTO 단에서 필요한 정보 : reviewTitle  reviewMountainName reviewContent mountainUniqueNo (4가지)
    //스프링 빈 검증을 사용하면 리다이렉트가 아닌 정적 화면 리소스로 이동을 해야하는데, 리뷰를 작성하는 화면이 동적으로 생성된 페이지의 일부임
    // 그래서 정적 화면 리소스로 이동하게 되면, 동적으로 생성된 데이터들을 유지하지 못함(빈 화면 이동되겟지).
    // 리다이렉트를 하면 동적 데이터는 유지가 되지만, 리뷰 작성창 닫김, 틀린 내용 유지 불가, 틀린 내용 조언 출력 불가
    // 고로=> 스프링 빈 검증을 사용하지 않고 AJAX 로 먼저 입력값을 검증, 틀릴시 JS 의 alter 로 경고창을 띄우는 방식으로 진행..?


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


}
