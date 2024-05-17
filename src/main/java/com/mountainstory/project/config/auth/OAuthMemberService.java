package com.mountainstory.project.config.auth;

import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.repository.member.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class OAuthMemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final MemberRepository memberRepository;

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;


    public OAuthMemberService(HttpSession httpSession, MemberRepository memberRepository) {
        this.httpSession = httpSession;
        this.memberRepository = memberRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Member member = saveOrUpdate(attributes);

        String tokenValue = userRequest.getAccessToken().getTokenValue();
        httpSession.setAttribute("Member", new OAuthMemberSession(member,tokenValue));

        return new DefaultOAuth2User(null,
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member oAuthMember = memberRepository.findById(attributes.getId())
                .map(entity -> entity.update(attributes.getName()))
                .orElse(attributes.toEntity());
        return memberRepository.save(oAuthMember);
    }

    public void checkMemberLoginType(OAuthMemberSession oAuthMemberSession, Model model){
        if(oAuthMemberSession != null && oAuthMemberSession.getType().equals("naver")){
            model.addAttribute("naver","naver");
        } else if(oAuthMemberSession != null && oAuthMemberSession.getType().equals("kakao")) {
            model.addAttribute("kakao","kakao");
        }
    }

    public String kakaoMemberDelete(String accessToken,String userId){
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com/v1/user/unlink")
                .queryParam("Authorization","Bearer"+accessToken)
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
        log.info("카카오 탈퇴>{}",forEntity.getBody());
        return forEntity.getBody();
    }


    public String naverMemberLogout(String accessToken){
        URI uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/token")
                .queryParam("grant_type","delete")
                .queryParam("client_id",naverClientId)
                .queryParam("client_secret",naverClientSecret)
                .queryParam("access_token",accessToken)
                .queryParam("service_provider","NAVER")
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
        return forEntity.getBody();
    }


    //네이버 재인증 메소드. 현재 사용중이지 않다.
    public String naverRecertification(){
        URI uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("response_type","code")
                .queryParam("client_id",naverClientId)
                .queryParam("redirect_uri","http://localhost:8080/login/oauth2/code/naver")
                .queryParam("state","test")
                .queryParam("auth_type","reauthenticate")
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
        return forEntity.getBody();
    }
}
