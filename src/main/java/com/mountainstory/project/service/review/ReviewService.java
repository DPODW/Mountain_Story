package com.mountainstory.project.service.review;

import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    void createReviewInfo(ReviewInfo reviewInfo, Member memberInfo);

    List<ReviewInfo> findReviewList(String mountainUniqueNo);


}
