package com.mountainstory.project.repository.review;

import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindReviewInFoRepo {

    Page<Review> findReviewRatingStat(Member memberId, boolean ratingStat, Pageable pageable);

    List<Review> findTop7GoodReview();
}
