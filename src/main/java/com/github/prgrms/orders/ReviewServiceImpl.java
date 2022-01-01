package com.github.prgrms.orders;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Long createReview(Review review, Long orderId) {
        return reviewRepository.createReview(review, orderId);
    }

}
