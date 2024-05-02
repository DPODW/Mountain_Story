package com.mountainstory.project.repository.review;

import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import com.mountainstory.project.entity.user.ReviewRatingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindReviewRatingRepo {

    Page<Review> findReviewRatingGoodOrBad(Member memberId, boolean ratingStat, Pageable pageable);
}
