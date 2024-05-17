package com.mountainstory.project.controller;

import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.service.review.ReviewService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
@Component
public class ReviewRankingHelper {

    private final ReviewService reviewService;

    public ReviewRankingHelper(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    protected void findTop7GoodReview(Model model) {
        List<ReviewInfo> top7GoodReview = reviewService.findTop7GoodReview();
        model.addAttribute("top7GoodReviewList",top7GoodReview);
    }
}
