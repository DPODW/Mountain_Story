package com.mountainstory.project.config.auth.session;

import com.mountainstory.project.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class OAuthMemberSession implements Serializable {

    private String name;
    private String email;
    private String id;
    private String type;

    private String accessToken;

    public OAuthMemberSession(Member member,String accessToken) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.id= member.getId();
        this.type = member.getType();
        this.accessToken = accessToken;
    }
}
