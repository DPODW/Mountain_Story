package com.mountainstory.project.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
public class Member {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "MEMBER_NUMBER")
//    private Long number;

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

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


    public Member createMemberInfo(String email,String name,LocalDateTime createTime,String id,String type){
        this.email=email;
        this.type=type;
        this.name=name;
        this.createTime=createTime;
        this.id=id;
        return this;
        //회원 정보를 만들때 사용되는 생성자
    }

    public Member update(String name){
        this.name=name;
        return this;
    }
}
