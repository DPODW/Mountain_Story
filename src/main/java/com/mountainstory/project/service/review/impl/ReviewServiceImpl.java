package com.mountainstory.project.service.review.impl;

import com.mountainstory.project.config.auth.session.OAuthMemberSession;
import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import com.mountainstory.project.entity.user.ReviewRatingHistory;
import com.mountainstory.project.repository.review.ReviewRatingHistoryRepository;
import com.mountainstory.project.repository.review.ReviewRepository;
import com.mountainstory.project.service.review.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewRatingHistoryRepository reviewRatingHistoryRepository;

    private final static String REVIEW_RATING_GOOD = "good";

    private final static String REVIEW_RATING_BAD = "bad";


    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewRatingHistoryRepository reviewRatingHistoryRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewRatingHistoryRepository = reviewRatingHistoryRepository;
    }

    @Override
    public void createReviewInfo(ReviewInfo reviewInfo, OAuthMemberSession oAuthMemberSession) {
        Review review = new Review();
        Member member = new Member();
        member.getMemberInfo(oAuthMemberSession.getEmail(), oAuthMemberSession.getName(), oAuthMemberSession.getId(), oAuthMemberSession.getType());

        Review reviewInfoResult = review.createReviewInfo(member, reviewInfo.getReviewMountainName(),
                reviewInfo.getMountainUniqueNo(),reviewInfo.getReviewContent(), reviewInfo.getReviewTitle(), LocalDateTime.now());

        reviewRepository.save(reviewInfoResult);
    }

    @Override
    public Page<ReviewInfo> findReviewList(String mountainUniqueNo, Pageable pageable) {
        Page<Review> mountainReviewList = reviewRepository.findAllByMountainUniqueNo(mountainUniqueNo,pageable);

        List<ReviewInfo> reviewInfoList = mountainReviewList.stream()
                .map(mountainReview -> {
                    ReviewInfo reviewInfo = reviewEntityToDto(mountainReview);
                    return reviewInfo;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(reviewInfoList,pageable, mountainReviewList.getTotalElements());
    }


    @Override
    public void reviewRatingPlus(Long mountainReviewNumber,String reviewRatingStat,OAuthMemberSession oAuthMemberSession) {
        Member member = new Member();
        member.getMemberInfo(oAuthMemberSession.getEmail(), oAuthMemberSession.getName(), oAuthMemberSession.getId(), oAuthMemberSession.getType());

        if(reviewRatingHistoryRepository.findByMemberIdAndReviewNumber(member,mountainReviewNumber)!=null){
          throw new DuplicateKeyException("이미 "+ mountainReviewNumber + " 번 게시글에 평가를 완료한 회원입니다.");
        }

        Review reviewInfo = reviewRepository.getReferenceById(mountainReviewNumber);
        ReviewRatingHistory reviewRatingHistory = new ReviewRatingHistory();

        if(reviewRatingStat.equals(REVIEW_RATING_GOOD)){
            reviewRatingHistory.createReviewRatingHistory(mountainReviewNumber,member,true);
            reviewRatingHistoryRepository.save(reviewRatingHistory);

            reviewInfo.setReviewGoodCount(reviewInfo.getReviewGoodCount()+1);
            reviewRepository.save(reviewInfo);
        }else if(reviewRatingStat.equals(REVIEW_RATING_BAD)){
            reviewRatingHistory.createReviewRatingHistory(mountainReviewNumber,member,false);
            reviewRatingHistoryRepository.save(reviewRatingHistory);

            reviewInfo.setReviewBadCount(reviewInfo.getReviewBadCount()+1);
            reviewRepository.save(reviewInfo);
        }

    }

    @Override
    public Page<ReviewInfo> findMemberReviewHistory(OAuthMemberSession oAuthMemberSession, Pageable pageable) {
        Member member = new Member().getMemberInfo(oAuthMemberSession.getEmail(), oAuthMemberSession.getName(),oAuthMemberSession.getId(), oAuthMemberSession.getType());
        Page<Review> findReviewHistory = reviewRepository.findByMember(member,pageable);

        List<ReviewInfo> reviewDto = findReviewHistory.stream()
                .map(reviewHistory -> {
                    ReviewInfo reviewInfo = reviewEntityToDto(reviewHistory);
                    return reviewInfo;
                }).collect(Collectors.toList());

        return new PageImpl<>(reviewDto,pageable,findReviewHistory.getTotalElements());
    }

    private static ReviewInfo reviewEntityToDto(Review review) {
        ReviewInfo reviewInfo = new ReviewInfo();
        reviewInfo.setReviewTitle(review.getReviewTitle());
        reviewInfo.setReviewMountainName(review.getMountainName());
        reviewInfo.setReviewContent(review.getReviewContent());
        reviewInfo.setMountainUniqueNo(review.getMountainUniqueNo());
        reviewInfo.setCreateTime(review.getCreateTime().toString()
                .substring(0, review.getCreateTime().toString().indexOf("T")));

        reviewInfo.setReviewWriter(review.getMember().getName());
        reviewInfo.setReviewNumber(review.getReviewNumber());
        reviewInfo.setReviewRatingGoodCount(review.getReviewGoodCount());
        reviewInfo.setReviewRatingBadCount(review.getReviewBadCount());
        return reviewInfo;
    }
}
