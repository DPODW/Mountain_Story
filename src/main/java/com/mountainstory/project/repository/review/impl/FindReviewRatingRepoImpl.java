package com.mountainstory.project.repository.review.impl;

import com.mountainstory.project.entity.user.*;
import com.mountainstory.project.repository.review.FindReviewRatingRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
public class FindReviewRatingRepoImpl implements FindReviewRatingRepo {

    private final JPAQueryFactory jpaQueryFactory;

    public FindReviewRatingRepoImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Review> findReviewRatingGoodOrBad(Member memberId, boolean ratingStat, Pageable pageable) {
        QReviewRatingHistory qReviewRatingHistory = QReviewRatingHistory.reviewRatingHistory;
        QReview qReview = QReview.review;

        List<Long> reviewRatingHistoryList =  jpaQueryFactory
                .select(qReviewRatingHistory.reviewNumber)
                .from(qReviewRatingHistory)
                .where(qReviewRatingHistory.isReviewed.eq(ratingStat).and(qReviewRatingHistory.memberId.eq(memberId)))
                .fetch();

        List<Review> reviewRatingList = jpaQueryFactory
                .select(qReview)
                .from(qReview)
                .where(qReview.reviewNumber.in(reviewRatingHistoryList))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qReview.reviewNumber.desc())
                .fetch();

        Long count = jpaQueryFactory
                .select(qReview.count())
                .from(qReview)
                .where(qReview.reviewNumber.in(reviewRatingHistoryList))
                .fetchOne();

        return new PageImpl<>(reviewRatingList,pageable,count);
    }
}
