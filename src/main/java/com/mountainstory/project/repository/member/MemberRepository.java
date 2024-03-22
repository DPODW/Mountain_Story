package com.mountainstory.project.repository.member;

import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findById(String id);




}
