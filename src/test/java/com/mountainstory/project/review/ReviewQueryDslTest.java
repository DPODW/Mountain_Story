package com.mountainstory.project.review;

import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import com.mountainstory.project.repository.review.FindReviewInFoRepo;
import com.mountainstory.project.repository.review.impl.FindReviewInFoRepoImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewQueryDslTest {

    private FindReviewInFoRepo findReviewInFoRepo;


    @Autowired
    public ReviewQueryDslTest(FindReviewInFoRepo findReviewInFoRepo) {
        this.findReviewInFoRepo = findReviewInFoRepo;
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
            return new JPAQueryFactory(entityManager);
        }

        @Bean
        public FindReviewInFoRepo findReviewInFoRepo(JPAQueryFactory jpaQueryFactory) {
            return new FindReviewInFoRepoImpl(jpaQueryFactory);
        }
    }



    @Test
    @DisplayName("QueryDsl 를 활용하여 좋아요 누른 게시글 & 싫어요 누른 게시글 조회")
    void findReviewRatingStat(){
        /*
        * 실제 가입된 ID 를 기준으로, 좋아요 & 싫어요 누른 게시글 리스트 조회 테스트
        * createMemberInfo ID 는 실제 가입된 ID 를 작성해야하고, 나머지는 TEST 형식 유지
        *
        * then : ID : 3292075458 회원은 좋아요 누른 게시글 2개 / 싫어요 누른 게시글 1개 (실제 DB 기준)
        * */


        /* given */
        Member member = new Member();
        member.createMemberInfo("test@test.com","testName","3292075458","testType", LocalDateTime.now());

        boolean ratingStatGood = true;
        boolean ratingStatBad = false;

        Pageable pageable = PageRequest.of(0, 10);

        /* when */
        Page<Review> GoodRateList = findReviewInFoRepo.findReviewRatingStat(member,ratingStatGood,pageable);
        Page<Review> BadRateList = findReviewInFoRepo.findReviewRatingStat(member,ratingStatBad,pageable);

        /* then */
        assertThat(GoodRateList.getTotalElements()).isEqualTo(2);
        assertThat(BadRateList.getTotalElements()).isEqualTo(1);

    }



    @Test
    @DisplayName("QueryDsl 를 활용하여 추천수 많은 리뷰 상위 7개 조회")
    void findTop7GoodReview(){
        /* 실제 DB에 리뷰가 최소 7개 이상 있어야 함
        *  마지막 테스트의 expected 값으로 제일 높은 추천수를 가진 리뷰의 추천수를 작성하면 됌
        * */

        /* when */
        List<Review> top7GoodReview = findReviewInFoRepo.findTop7GoodReview();

        /* then */
        assertThat(top7GoodReview.size()).isEqualTo(7);
        assertThat(top7GoodReview.get(0).getReviewGoodCount()).isEqualTo(8);

    }





}
