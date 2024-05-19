package com.mountainstory.project.repository.review.impl;

import com.mountainstory.project.entity.user.*;
import com.mountainstory.project.repository.review.FindReviewInFoRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
public class FindReviewInFoRepoImpl implements FindReviewInFoRepo {

    private final JPAQueryFactory jpaQueryFactory;

    public FindReviewInFoRepoImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Review> findReviewRatingStat(Member memberId, boolean ratingStat, Pageable pageable) {
        QReviewRatingHistory qReviewRatingHistory = QReviewRatingHistory.reviewRatingHistory;
        QReview qReview = QReview.review;

        List<ReviewRatingHistory> reviewRatingHistoryList = jpaQueryFactory
                .selectFrom(qReviewRatingHistory)
                .where(qReviewRatingHistory.isReviewed.eq(ratingStat).and(qReviewRatingHistory.memberId.eq(memberId)))
                .fetch();

        List<Review> reviewRatingList = jpaQueryFactory
                .select(qReview)
                .from(qReview)
                .where(qReview.reviewNumber.in(reviewRatingHistoryList))//반복하면서 숫자 가져오기. . .?
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

    @Override
    public List<Review> findTop7GoodReview() {
        QReview qReview = QReview.review;

        List<Review> reviewTop7GoodList = jpaQueryFactory
                .select(qReview)
                .from(qReview)
                .orderBy(qReview.reviewGoodCount.desc())
                .limit(7)
                .fetch();

        return reviewTop7GoodList;
    }
}
