package com.mountainstory.project.entity.user;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ReviewRatingHistory {

    @Id
    @Column(name = "REVIEW_HISTORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewHistoryId;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member memberId;

    @Column(name = "REVIEW_NUMBER")
    private Long reviewNumber;

    @Column(name = "IS_REVIEWED")
    private boolean isReviewed;


    public ReviewRatingHistory createReviewRatingHistory(Long reviewNumber, Member memberId, boolean isReviewed) {
        this.reviewNumber = reviewNumber;
        this.memberId = memberId;
        this.isReviewed = isReviewed;
        return this;
    }
}
