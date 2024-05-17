package com.mountainstory.project.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
public class Member {
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



    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<Review>();


    @OneToMany(mappedBy = "memberId", cascade = CascadeType.REMOVE)
    private List<ReviewRatingHistory> reviewRatingHistory = new ArrayList<ReviewRatingHistory>();


    public Member createMemberInfo(String email,String name,String id,String type,LocalDateTime createTime){
        this.email=email;
        this.type=type;
        this.name=name;
        this.createTime=createTime;
        this.id=id;
        return this;
        //회원 정보를 만들때(회원가입) 사용되는 생성자
    }

    public Member getMemberInfo(String email,String name,String id,String type){
        this.email=email;
        this.type=type;
        this.name=name;
        this.id=id;
        return this;
        //회원 정보를 받아올때 (기존회원) 사용되는 생성자
    }

    public Member update(String name){
        this.name=name;
        return this;
    }
}
