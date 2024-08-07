package com.mountainstory.project.controller;

import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.config.exception.AjaxException;
import com.mountainstory.project.controller.paging.PagingFunction;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.dto.user.MemberInfo;
import com.mountainstory.project.service.member.MemberService;
import com.mountainstory.project.service.review.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    private final OAuthMemberService oAuthMemberService;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final PagingFunction pagingFunction;
    private final ReviewRankingHelper reviewRankingHelper;

    public MemberController(OAuthMemberService oAuthMemberService, ReviewService reviewService, MemberService memberService, PagingFunction pagingFunction, ReviewRankingHelper reviewRankingHelper) {
        this.oAuthMemberService = oAuthMemberService;
        this.reviewService = reviewService;
        this.memberService = memberService;
        this.pagingFunction = pagingFunction;
        this.reviewRankingHelper = reviewRankingHelper;
    }

    @GetMapping("/review/history")
    public String memberReviewHistory(@PageableDefault(page=0, size=10, sort="reviewNumber", direction= Sort.Direction.DESC) Pageable pageable,
                                      @LoginMember OAuthMemberSession oAuthMemberSession, Model model){
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        reviewRankingHelper.findTop7GoodReview(model);
        Page<ReviewInfo> reviewHistory = reviewService.findMemberReviewHistory(oAuthMemberSession,pageable);

        model.addAttribute("reviewHistory",reviewHistory);
        model.addAttribute("loginMember",oAuthMemberSession);

        pagingFunction.getPagingDataAndModel(reviewHistory,model);
        return "main/memberReviewHistory";
    }

    @GetMapping("/review/rating/stat/list/{ratingStat}")
    public String memberReviewGoodOrBadList(@PageableDefault(page=0, size=10) Pageable pageable, @PathVariable boolean ratingStat,
                                            @LoginMember OAuthMemberSession oAuthMemberSession, Model model){
        //리뷰 상태별 리스트의 정렬은 쿼리 DSL 부분에서 ORDER 절로 처리함.
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        reviewRankingHelper.findTop7GoodReview(model);
        Page<ReviewInfo> reviewStatList = reviewService.findReviewStatHistory(oAuthMemberSession, ratingStat, pageable);

        model.addAttribute("ratingStat",ratingStat);
        model.addAttribute("loginMember",oAuthMemberSession);
        model.addAttribute("reviewGoodOrBadList",reviewStatList);

        pagingFunction.getPagingDataAndModel(reviewStatList,model);
        return "main/memberReviewRatingHistory";
    }

    @GetMapping("/myInfo")
    public String memberMyInfo(@LoginMember OAuthMemberSession oAuthMemberSession,Model model){
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        reviewRankingHelper.findTop7GoodReview(model);
        MemberInfo memberInfoById = memberService.findMemberInfoById(oAuthMemberSession.getId());

        model.addAttribute("loginMember",oAuthMemberSession);
        model.addAttribute("memberInfoById",memberInfoById);

        return "main/memberInfo";
    }

    @ResponseStatus(HttpStatus.SEE_OTHER) //응답 상태 정의 (정의 안하면 delete 방식으로 재요청되기 때문)
    @DeleteMapping("/delete")
    public String memberInfoDelete(@LoginMember OAuthMemberSession oAuthMemberSession, HttpServletRequest request){
        memberService.deleteMemberById(oAuthMemberSession.getId());
        HttpSession session = request.getSession(false);

        if (oAuthMemberSession!=null && session.getAttribute("Member")!=null){
            if(oAuthMemberSession.getType().equals("naver")){
                oAuthMemberService.naverMemberLogout(oAuthMemberSession.getAccessToken());
            }else {
                oAuthMemberService.kakaoMemberDelete(oAuthMemberSession.getAccessToken());
            }
        }
        return "redirect:/logout";
    }

    @PostMapping("/access/check")
    public ResponseEntity<String> memberAccessCheck(@LoginMember OAuthMemberSession oAuthMemberSession){
       if (oAuthMemberSession==null){
           throw new AjaxException("회원 아님");
       }else
           return ResponseEntity.ok("ok");
    }

    @PostMapping("/reviewer/check")
    public ResponseEntity<String> reviewWriterCheck(@LoginMember OAuthMemberSession oAuthMemberSession, @RequestParam String reviewerId){
        if(oAuthMemberSession!=null && oAuthMemberSession.getId().equals(reviewerId)){
            return ResponseEntity.ok("ok");
        }else{
            throw new AjaxException("error");
        }
    }
}
