package com.mountainstory.project.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Comment {

    @Id
    @Column(name = "COMMENT_NUMBER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNumber;

    @ManyToOne
    @JoinColumn(name = "MEMBER_EMAIL")
    private Member member;


    @ManyToOne
    @JoinColumn(name = "MOUNTAIN_REVIEW_NUMBER")
    private Review review;

    @Column(name = "COMMENT_CONTENT")
    private String commentContent;

    @Column(name = "COMMENT_WRITE_DATE")
    private LocalDateTime createTime;
}
