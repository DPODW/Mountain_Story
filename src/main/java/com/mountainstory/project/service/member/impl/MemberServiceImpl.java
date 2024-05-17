package com.mountainstory.project.service.member.impl;

import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.dto.user.MemberInfo;
import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.repository.member.MemberRepository;
import com.mountainstory.project.service.member.MemberService;
import com.mountainstory.project.service.review.ReviewService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public void deleteMemberById(String memberId) {
        memberRepository.deleteMemberById(memberId);

    }


    @Override
    public MemberInfo findMemberInfoById(String id) {
        Optional<Member> memberEntity = memberRepository.findById(id);
        Member uniqueMember = memberEntity.get();

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberName(uniqueMember.getName());
        memberInfo.setMemberEmail(uniqueMember.getEmail());
        memberInfo.setMemberType(uniqueMember.getType());
        memberInfo.setMemberJoinDate(uniqueMember.getCreateTime().toString().substring
                (0,uniqueMember.getCreateTime().toString().indexOf("T")));
        //TODO: 가입시간 보기 좋게 수정 필요

        return memberInfo;
    }
}
