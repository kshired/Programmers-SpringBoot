package com.github.prgrms.orders;

import java.time.LocalDateTime;

public class OrderResponse {
    private Long seq;
    private Long productId;
    private ReviewDto review;
    private OrderStatus state;
    private String requestMessage;
    private String rejectMessage;
    private LocalDateTime completedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime createAt;

    protected OrderResponse(){};

    public OrderResponse(Orders source){
        this.seq = source.getSeq();
        this.productId = source.getProductSeq();
        this.review = source.getReview() == null ? null : new ReviewDto(source.getReview());
        this.state = source.getState();
        this.requestMessage = source.getRequestMsg();
        this.rejectMessage = source.getRejectMsg();
        this.completedAt = source.getCompletedAt();
        this.rejectedAt = source.getRejectedAt();
        this.createAt = source.getCompletedAt();
    }
}
