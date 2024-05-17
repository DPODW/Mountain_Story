package com.mountainstory.project.controller;


import com.mountainstory.project.config.auth.OAuthMemberService;
import com.mountainstory.project.config.auth.session.LoginMember;
import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.controller.paging.PagingFunction;
import com.mountainstory.project.dto.mountain.mountainWeather.MountainWeather;
import com.mountainstory.project.dto.mountain.mountaininfo.MountainInfoDto;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.dto.user.MemberInfo;
import com.mountainstory.project.service.mountain.mountaininfo.MountainInfoService;
import com.mountainstory.project.service.mountain.mountainweather.MountainWeatherService;
import com.mountainstory.project.service.review.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RequestMapping("/mountain/info/search")
@Controller
public class MountainInfoController {
    private final MountainInfoService mountainInfoService;

    private final MountainWeatherService mountainWeatherService;

    private final OAuthMemberService oAuthMemberService;

    private final ReviewService reviewService;

    private final PagingFunction pagingFunction;
    private final ReviewRankingHelper reviewRankingHelper;

    public MountainInfoController(MountainInfoService mountainInfoService, MountainWeatherService mountainWeatherService, OAuthMemberService oAuthMemberService, ReviewService reviewService, PagingFunction pagingFunction, ReviewRankingHelper reviewRankingHelper) {
        this.mountainInfoService = mountainInfoService;
        this.mountainWeatherService = mountainWeatherService;
        this.oAuthMemberService = oAuthMemberService;
        this.reviewService = reviewService;
        this.pagingFunction = pagingFunction;
        this.reviewRankingHelper = reviewRankingHelper;
    }

    //TODO:get~ 메소드가 존재하니까 api 들은 범위를 제한해도될듯


    @GetMapping("/one/{mountainIndex}")
    public String mountainInfoOne(@PageableDefault(page=0, size=10, sort="reviewNumber", direction=Sort.Direction.DESC) Pageable pageable, @LoginMember OAuthMemberSession oAuthMemberSession, @PathVariable(name = "mountainIndex") Integer mountainIndex,
                                  HttpServletRequest request, Model model) throws UnsupportedEncodingException, ParseException {
        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        reviewRankingHelper.findTop7GoodReview(model);
        HttpSession session = request.getSession(false);

        List<MountainInfoDto> allMountainInfoList = (List<MountainInfoDto>) session.getAttribute("allMountainInfoList");
        //TODO: 세션 무효화를 하지 않아야, 새로고침이 이루어짐. (무효화는 필수이기 때문에, 추후 무효화 로직을 해당 메소드에 영향을 받지 않는 곳에 구현해야함

        MountainInfoDto mountainInfoOne = mountainInfoService.setCoordinateInfo(allMountainInfoList.get(mountainIndex));
        MountainWeather mountainWeather = mountainWeatherService.getMountainWeather(mountainInfoOne.getMountainCoordinate(), mountainInfoOne.getMountainLocation());
        Page<ReviewInfo> reviewList = reviewService.findReviewList(mountainInfoOne.getMountainNo(),pageable);

        model.addAttribute("loginMember",oAuthMemberSession);

        model.addAttribute("mountainInfoOne",mountainInfoOne);
        model.addAttribute("mountainWeather",mountainWeather);
        model.addAttribute("mountainIndex",mountainIndex);

        pagingFunction.getPagingDataAndModel(reviewList,model);
        model.addAttribute("reviewList", reviewList);
        return "main/SearchResult";
    }


    @GetMapping("/list")
    public String mountainInfoList(@LoginMember OAuthMemberSession oAuthMemberSession, @RequestParam(name = "mountainName") String mountainName, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        model.addAttribute("showLoading",true);

        oAuthMemberService.checkMemberLoginType(oAuthMemberSession,model);
        reviewRankingHelper.findTop7GoodReview(model);

        List<MountainInfoDto> allMountainInfoList = mountainInfoService.getAllMountainInfoList(mountainName);

        if(allMountainInfoList.size()!=0){
            HttpSession session = request.getSession(false);
            session.setAttribute("allMountainInfoList",allMountainInfoList);
        }

        model.addAttribute("loginMember",oAuthMemberSession);
        model.addAttribute("mountainInfoList",allMountainInfoList);
        return "main/SearchResultList";
    }

}
