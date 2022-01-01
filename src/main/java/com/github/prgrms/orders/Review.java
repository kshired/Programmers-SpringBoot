package com.github.prgrms.orders;

import java.time.LocalDateTime;

public class Review {
    private Long seq;
    private Long userSeq;
    private Long productSeq;
    private String content;
    private LocalDateTime createAt;

    public Long getSeq() {
        return seq;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public Long getProductSeq() {
        return productSeq;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Review(Long userSeq, Long productSeq, String content, LocalDateTime createAt) {
        this.userSeq = userSeq;
        this.productSeq = productSeq;
        this.content = content;
        this.createAt = createAt;
    }

    public Review(Long seq, Long userSeq, Long productSeq, String content, LocalDateTime createAt) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.productSeq = productSeq;
        this.content = content;
        this.createAt = createAt;
    }
}
