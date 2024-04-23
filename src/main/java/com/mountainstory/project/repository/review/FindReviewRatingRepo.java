package com.mountainstory.project.repository.review;

import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.ReviewRatingHistory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FindReviewRatingRepo {

    Page<ReviewRatingHistory> findReviewRatingGoodOrNot(Member memberId, boolean ratingStat);
}
