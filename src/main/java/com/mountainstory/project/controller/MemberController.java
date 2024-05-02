package com.mountainstory.project.controller;

import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.controller.paging.PagingFunction;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.service.member.MemberService;
import com.mountainstory.project.service.review.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final ReviewService reviewService;

    private final PagingFunction pagingFunction;

    public MemberController(MemberService memberService, ReviewService reviewService, PagingFunction pagingFunction) {
        this.memberService = memberService;
        this.reviewService = reviewService;
        this.pagingFunction = pagingFunction;
    }

    @GetMapping("/review/history")
    public String memberReviewHistory(@PageableDefault(page=0, size=5, sort="reviewNumber", direction= Sort.Direction.DESC) Pageable pageable,
                                      @LoginMember OAuthMemberSession oAuthMemberSession, Model model){
        Page<ReviewInfo> reviewHistory = reviewService.findMemberReviewHistory(oAuthMemberSession,pageable);

        model.addAttribute("reviewHistory",reviewHistory);
        model.addAttribute("loginMember",oAuthMemberSession);

        pagingFunction.getPagingDataAndModel(reviewHistory,model);
        return "main/memberReviewHistory";
    }

    @GetMapping("/review/rating/stat/list/{ratingStat}")
    public String memberReviewGoodOrBadList(@PageableDefault(page=0, size=2) Pageable pageable, @PathVariable boolean ratingStat,
                                            @LoginMember OAuthMemberSession oAuthMemberSession, Model model){
        //리뷰 상태별 리스트의 정렬은 쿼리 DSL 부분에서 ORDER 절로 처리함.
        Page<ReviewInfo> reviewStatList = reviewService.findReviewStatHistory(oAuthMemberSession, ratingStat, pageable);

        model.addAttribute("ratingStat",ratingStat);
        model.addAttribute("loginMember",oAuthMemberSession);
        model.addAttribute("reviewGoodOrBadList",reviewStatList);

        pagingFunction.getPagingDataAndModel(reviewStatList,model);
        return "main/memberReviewRatingHistory";

    }
}
