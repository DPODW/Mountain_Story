package com.mountainstory.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_EMAIL")
    private String email;

    @Column(name = "MEMBER_TYPE")
    private String type;

    @Column(name = "MEMBER_NAME")
    private String name;

    @Column(name = "MEMBER_JOIN_DATE")
    private LocalDateTime createTime;

    //TODO: 추후에 반환 타입을 DTO 로 하는게 좋다. (리팩토링 필요)
    //양방향 관계를 위한 정의
    @OneToMany(mappedBy = "member")
    private List<Review> reviewList = new ArrayList<Review>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<Comment>();
}
