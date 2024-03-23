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
        Member member = new Member();
        Member memberInfo = member.createMemberInfo(oAuthMemberSession.getEmail(), oAuthMemberSession.getName(),
                oAuthMemberSession.getId(), oAuthMemberSession.getType(), LocalDateTime.now());

        reviewService.createReviewInfo(reviewInfo,memberInfo);

        return "redirect:/mountain/info/search/one/"+mountainIndex;
    }

    @PostMapping("/good/{reviewNumber}")
    public ResponseEntity<String> reviewGood(@PathVariable Long reviewNumber){
        log.info("프론트에서 가져온 리뷰 넘버 >>{}",reviewNumber);
        //카운트 수 (+1) 도 프론트 쪽에서 가져와도 되기는 하나, 보안을 위해 백엔드 로직에서 +1 해줄 예정 (서비스 단)
        reviewService.plusGoodCount(reviewNumber);
        return null;
    }

    //한개의 컨트롤러로 good bad 를 모두 받을지 아니면 두개의 컨트롤러로 분할해서 받을지. . .
}
