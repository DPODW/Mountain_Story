package com.mountainstory.project.controller;

import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.config.exception.AjaxException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    private final OAuthMemberService oAuthMemberService;
    private final ReviewService reviewService;

    private final PagingFunction pagingFunction;

    public MemberController(OAuthMemberService oAuthMemberService, ReviewService reviewService, PagingFunction pagingFunction) {
        this.oAuthMemberService = oAuthMemberService;
        this.reviewService = reviewService;
        this.pagingFunction = pagingFunction;
    }

    @GetMapping("/review/history")
    public String memberReviewHistory(@PageableDefault(page=0, size=10, sort="reviewNumber", direction= Sort.Direction.DESC) Pageable pageable,
                                      @LoginMember OAuthMemberSession oAuthMemberSession, Model model){
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        Page<ReviewInfo> reviewHistory = reviewService.findMemberReviewHistory(oAuthMemberSession,pageable);
        List<ReviewInfo> top7GoodReview = reviewService.findTop7GoodReview();

        model.addAttribute("reviewHistory",reviewHistory);
        model.addAttribute("loginMember",oAuthMemberSession);
        model.addAttribute("top7GoodReviewList",top7GoodReview);

        pagingFunction.getPagingDataAndModel(reviewHistory,model);
        return "main/memberReviewHistory";
    }

    @GetMapping("/review/rating/stat/list/{ratingStat}")
    public String memberReviewGoodOrBadList(@PageableDefault(page=0, size=10) Pageable pageable, @PathVariable boolean ratingStat,
                                            @LoginMember OAuthMemberSession oAuthMemberSession, Model model){
        //리뷰 상태별 리스트의 정렬은 쿼리 DSL 부분에서 ORDER 절로 처리함.
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        Page<ReviewInfo> reviewStatList = reviewService.findReviewStatHistory(oAuthMemberSession, ratingStat, pageable);
        List<ReviewInfo> top7GoodReview = reviewService.findTop7GoodReview();

        model.addAttribute("ratingStat",ratingStat);
        model.addAttribute("loginMember",oAuthMemberSession);
        model.addAttribute("reviewGoodOrBadList",reviewStatList);
        model.addAttribute("top7GoodReviewList",top7GoodReview);

        pagingFunction.getPagingDataAndModel(reviewStatList,model);
        return "main/memberReviewRatingHistory";
    }

    @PostMapping("/access/check")
    public ResponseEntity<String> memberAccessCheck(@LoginMember OAuthMemberSession oAuthMemberSession){
       if (oAuthMemberSession==null){
           throw new AjaxException("회원 아님");
       }else
           return ResponseEntity.ok("ok");
    }
}
