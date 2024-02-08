package com.mountainstory.project.config.auth;

import com.mountainstory.project.entity.user.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Setter
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String id;
    private String type;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email,String id,String type) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.id=id;
        this.type=type;
    }

    public static OAuthAttributes of(String registrationId,String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver(userNameAttributeName,attributes);
        }
        return ofKakao(userNameAttributeName,attributes);
    }

    //카카오는 사용자정보 응답 JSON 에서, kakao_account 안에 name 과 email 이 있다, 고로 먼저 kakao_account 를 꺼내고 거기서 이름과 메일을 반환받아야 한다.
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes){
        OAuthAttributes KakaoAttribute = new OAuthAttributes(attributes,userNameAttributeName,null,null,null,"kakao");

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        KakaoAttribute.setId( String.valueOf(attributes.get("id")));
        KakaoAttribute.setName((String) profile.get("nickname"));
        KakaoAttribute.setEmail((String) kakaoAccount.get("email"));
        return KakaoAttribute;
    }


    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        OAuthAttributes NaverAttribute = new OAuthAttributes(attributes,userNameAttributeName,null,null,null,"naver");

        Map<String, Object> naverProfile = (Map<String, Object>) attributes.get("response");

        NaverAttribute.setId((String) naverProfile.get("id"));
        NaverAttribute.setName((String) naverProfile.get("name"));
        NaverAttribute.setEmail((String) naverProfile.get("email"));
        return NaverAttribute;
    }


    //가입시에 필요한 메소드 (이미 존재하는 회원이면 필요 x)
    public Member toEntity(){
        Member member = new Member();
        member.createMemberInfo(email,name,id,type,LocalDateTime.now());
        return member;
    }
}
