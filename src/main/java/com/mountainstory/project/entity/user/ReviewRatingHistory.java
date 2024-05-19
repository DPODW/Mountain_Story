package com.mountainstory.project.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@ToString
public class ReviewRatingHistory {

    @Id
    @Column(name = "REVIEW_HISTORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewHistoryId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "REVIEW_NUMBER")
    private Review reviewNumber;

    @Column(name = "IS_REVIEWED")
    private boolean isReviewed;


    public ReviewRatingHistory createReviewRatingHistory(Review reviewNumber, Member memberId, boolean isReviewed) {
        this.reviewNumber = reviewNumber;
        this.memberId = memberId;
        this.isReviewed = isReviewed;
        return this;
    }
}
