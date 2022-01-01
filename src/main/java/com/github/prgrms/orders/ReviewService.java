package com.github.prgrms.orders;


public interface ReviewService {
    Long createReview(Review review, Long orderId);
}
