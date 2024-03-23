package com.mountainstory.project.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class ReviewInfo {
    private String reviewTitle;
    private String reviewMountainName;
    private String reviewContent;
    private String mountainUniqueNo;
    private String createTime;
    private String reviewWriter;
    private Long reviewNumber;

    public ReviewInfo(String reviewTitle, String reviewMountainName, String reviewContent,String mountainUniqueNo) {
        this.reviewTitle = reviewTitle;
        this.reviewMountainName = reviewMountainName;
        this.reviewContent = reviewContent;
        this.mountainUniqueNo=mountainUniqueNo;
    }
}
