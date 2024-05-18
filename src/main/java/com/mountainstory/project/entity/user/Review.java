package com.mountainstory.project.entity.user;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@ToString
@Entity
public class Review {

    @Id
    @Column(name = "MOUNTAIN_REVIEW_NUMBER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNumber;


    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @Setter
    private Member member;

    @Column(name = "MOUNTAIN_NAME")
    private String mountainName;

    @Column(name = "MOUNTAIN_UNIQUE_NUMBER")
    private String mountainUniqueNo;

    @Column(name = "MOUNTAIN_REVIEW_CONTENT")
    private String reviewContent;

    @Column(name = "MOUNTAIN_REVIEW_TITLE")
    private String reviewTitle;

    @Column(name = "MOUNTAIN_REVIEW_WRITE_DATE")
    private LocalDateTime createTime;

    @Setter
    @Column(name = "REVIEW_GOOD_COUNT")
    private int reviewGoodCount;

    @Setter
    @Column(name = "REVIEW_BAD_COUNT")
    private int reviewBadCount;


    public Review createReviewInfo(Member member, String mountainName,String mountainUniqueNo, String reviewContent, String reviewTitle, LocalDateTime createTime) {
        this.member = member;
        this.mountainName = mountainName;
        this.mountainUniqueNo=mountainUniqueNo;
        this.reviewContent = reviewContent;
        this.reviewTitle = reviewTitle;
        this.createTime = createTime;
        return this;
    }
}


