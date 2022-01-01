package com.github.prgrms.orders;

import jdk.vm.ci.meta.Local;

import java.time.LocalDateTime;

public class Orders {
    private Long seq;
    private Long userSeq;
    private Long productSeq;
    private Long reviewSeq;
    private OrderStatus state;

    private String requestMsg;
    private String rejectMsg;
    private LocalDateTime completedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime createAt;
    private Review review;

    public Long getSeq() {
        return seq;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public Long getProductSeq() {
        return productSeq;
    }

    public Long getReviewSeq() {
        return reviewSeq;
    }

    public OrderStatus getState() {
        return state;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void setState(OrderStatus state) {
        this.state = state;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setUserSeq(Long userSeq) {
        this.userSeq = userSeq;
    }

    public void setProductSeq(Long productSeq) {
        this.productSeq = productSeq;
    }

    public void setReviewSeq(Long reviewSeq) {
        this.reviewSeq = reviewSeq;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Orders(Long seq, Long userSeq, Long productSeq, Long reviewSeq, OrderStatus state, String requestMsg, String rejectMsg, LocalDateTime completedAt, LocalDateTime rejectedAt, LocalDateTime createAt) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.productSeq = productSeq;
        this.reviewSeq = reviewSeq;
        this.state = state;
        this.requestMsg = requestMsg;
        this.rejectMsg = rejectMsg;
        this.completedAt = completedAt;
        this.rejectedAt = rejectedAt;
        this.createAt = createAt;
    }


    @Override
    public String toString() {
        return "Orders{" +
                "seq=" + seq +
                ", userSeq=" + userSeq +
                ", productSeq=" + productSeq +
                ", reviewSeq=" + reviewSeq +
                ", state=" + state +
                ", requestMsg='" + requestMsg + '\'' +
                ", rejectMsg='" + rejectMsg + '\'' +
                ", completedAt=" + completedAt +
                ", rejectedAt=" + rejectedAt +
                ", createAt=" + createAt +
                ", review=" + review +
                '}';
    }
}
