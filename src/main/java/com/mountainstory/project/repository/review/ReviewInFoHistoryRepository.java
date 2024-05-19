package com.mountainstory.project.repository.review;

import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import com.mountainstory.project.entity.user.ReviewRatingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewInFoHistoryRepository extends JpaRepository<ReviewRatingHistory,Long>, FindReviewInFoRepo {

    ReviewRatingHistory findByMemberIdAndReviewNumber(Member memberId, Review reviewNumber);


}
