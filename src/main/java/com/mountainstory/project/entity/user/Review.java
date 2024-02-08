package com.mountainstory.project.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Review {

    @Id
    @Column(name = "MOUNTAIN_REVIEW_NUMBER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNumber;


    @ManyToOne
    @JoinColumn(name = "MEMBER_EMAIL")
    private Member member;

    //TODO: 추후에 반환 타입을 DTO 로 하는게 좋다. (리팩토링 필요)
    //양방향 관계를 위한 정의
    @OneToMany(mappedBy = "review")
    private List<Comment> commentList = new ArrayList<Comment>();

    @Column(name = "MOUNTAIN_NAME")
    private String mountainName;

    @Column(name = "MOUNTAIN_REVIEW_CONTENT")
    private String reviewContent;

    @Column(name = "MOUNTAIN_REVIEW_WRITE_DATE")
    private LocalDateTime createTime;
}
