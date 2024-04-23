package com.mountainstory.project.repository.review.impl;

import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.QReviewRatingHistory;
import com.mountainstory.project.entity.user.ReviewRatingHistory;
import com.mountainstory.project.repository.review.FindReviewRatingRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;

import java.util.List;

public class FindReviewRatingRepoImpl implements FindReviewRatingRepo {

    private final JPAQueryFactory jpaQueryFactory;

    public FindReviewRatingRepoImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<ReviewRatingHistory> findReviewRatingGoodOrNot(Member memberId, boolean ratingStat) {
        QReviewRatingHistory qReviewRatingHistory = QReviewRatingHistory.reviewRatingHistory;

        List<ReviewRatingHistory> reviewRatingHistoryList =  jpaQueryFactory
                .select(qReviewRatingHistory)
                .from(qReviewRatingHistory)
                .where(qReviewRatingHistory.isReviewed.eq(ratingStat).and(qReviewRatingHistory.memberId.eq(memberId)))
                .fetch();

        /*TODO: 여기서부터 Pageing 객체로 반환해야 하는데, new PageImpl 을 사용해야 함.
        * count 개수를 구하는게 문제인데,  reviewRatingHistoryList 의 카운트만 반환하는 dsl 로직을
        * 추가하는 방법이 있음
        * ++ service 계층에서 안 될것 같음. -> service 계층에서도 마찬가지로 count 가 필요한데, count 속성을
        * Paging 객체여야만 사용할 수 있음. (dsl 로 반환한 객체는 list 임)
        * */

        return reviewRatingHistoryList;
    }
}
