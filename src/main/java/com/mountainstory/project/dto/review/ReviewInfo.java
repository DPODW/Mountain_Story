package com.mountainstory.project.dto.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class ReviewInfo {

    private String memberId;
    private String reviewTitle;

    private String reviewMountainName;

    private String reviewContent;

    private String mountainUniqueNo;

    private String createTime;

    private String reviewWriter;

    private Long reviewNumber;

    private int reviewRatingGoodCount;

    private int reviewRatingBadCount;


    public ReviewInfo(String memberId,String reviewTitle, String reviewMountainName, String reviewContent, String mountainUniqueNo, String createTime, String reviewWriter, Long reviewNumber, int reviewRatingGood, int reviewRatingBad) {
        this.memberId=memberId;
        this.reviewTitle = reviewTitle;
        this.reviewMountainName = reviewMountainName;
        this.reviewContent = reviewContent;
        this.mountainUniqueNo = mountainUniqueNo;
        this.createTime = createTime;
        this.reviewWriter = reviewWriter;
        this.reviewNumber = reviewNumber;
        this.reviewRatingGoodCount = reviewRatingGood;
        this.reviewRatingBadCount = reviewRatingBad;
    }
}
