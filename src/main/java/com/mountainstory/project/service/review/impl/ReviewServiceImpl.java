package com.mountainstory.project.service.review.impl;

import com.mountainstory.project.dto.review.ReviewInfo;
import com.mountainstory.project.entity.user.Member;
import com.mountainstory.project.entity.user.Review;
import com.mountainstory.project.repository.review.ReviewRepository;
import com.mountainstory.project.service.review.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void createReviewInfo(ReviewInfo reviewInfo, Member memberInfo) {
        Review review = new Review();
        Review reviewInfoResult = review.createReviewInfo(memberInfo, reviewInfo.getReviewMountainName(),
                reviewInfo.getMountainUniqueNo(),reviewInfo.getReviewContent(), reviewInfo.getReviewTitle(), LocalDateTime.now());

        reviewRepository.save(reviewInfoResult);
    }

    @Override
    public List<ReviewInfo> findReviewList(String mountainUniqueNo) {
        List<Review> mountainReviewList = reviewRepository.findAllByMountainUniqueNo(mountainUniqueNo, Sort.by(Sort.Direction.DESC,"reviewNumber"));

        List<ReviewInfo> reviewInfoList = mountainReviewList.stream()
                .map(mountainReview -> {
                    ReviewInfo reviewInfo = new ReviewInfo();
                    reviewInfo.setReviewTitle(mountainReview.getReviewTitle());
                    reviewInfo.setReviewMountainName(mountainReview.getMountainName());
                    reviewInfo.setReviewContent(mountainReview.getReviewContent());
                    reviewInfo.setMountainUniqueNo(mountainReview.getMountainUniqueNo());
                    reviewInfo.setCreateTime(mountainReview.getCreateTime().toString()
                            .substring(0,mountainReview.getCreateTime().toString().indexOf("T")));
                    reviewInfo.setReviewWriter(mountainReview.getMember().getName());
                    reviewInfo.setReviewNumber(mountainReview.getReviewNumber());
                    return reviewInfo;
                })
                .collect(Collectors.toList());

        return reviewInfoList;
    }

    @Override
    public void plusGoodCount(Long mountainReviewNumber) {
        Review referenceById = reviewRepository.getReferenceById(mountainReviewNumber);

        referenceById.setReviewGoodCount(referenceById.getReviewGoodCount()+1);
        reviewRepository.save(referenceById);
        log.info("추천 기능을 통해 얻은 good review 정보 {}",referenceById);

        //TODO: 추천수를 수정하는 로직 필요 (DAO OR 여기서 처리해야함)

    }

    @Override
    public void plusBadCount(Long mountainReviewNumber) {
        Review referenceById = reviewRepository.getReferenceById(mountainReviewNumber);
        log.info("추천 기능을 통해 얻은 bad review 정보 {}",referenceById);

        //TODO: 추천수를 수정하는 로직 필요 (DAO OR 여기서 처리해야함)
    }


}
