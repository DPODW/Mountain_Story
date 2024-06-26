package com.mountainstory.project.service.review;

import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import com.mountainstory.project.entity.user.ReviewRatingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    void createReviewInfo(ReviewInfo reviewInfo, OAuthMemberSession oAuthMemberSession);

    void deleteReviewById (Long reviewNumber);

    Page<ReviewInfo> findReviewList(String mountainUniqueNo, Pageable pageable);

    void reviewRatingPlus(Long mountainReviewNumber,boolean reviewRatingStat,OAuthMemberSession oAuthMemberSession);

    Page<ReviewInfo> findMemberReviewHistory(OAuthMemberSession oAuthMemberSession, Pageable pageable);

    Page<ReviewInfo> findReviewStatHistory(OAuthMemberSession oAuthMemberSession, boolean ratingStat,Pageable pageable);

    List<ReviewInfo> findTop7GoodReview();
}
