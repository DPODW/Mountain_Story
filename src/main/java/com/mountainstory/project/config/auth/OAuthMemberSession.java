package com.mountainstory.project.config.auth;

import com.mountainstory.project.entity.Member;

import java.io.Serializable;

public class OAuthMemberSession implements Serializable {

    private String name;
    private String email;

    public OAuthMemberSession(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
