package com.github.prgrms.orders;

import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ReviewDto {
    private Long seq;
    private Long productId;
    private String content;
    private LocalDateTime createAt;

    public ReviewDto(Review source){
        this.seq = source.getSeq();
        this.productId = source.getProductSeq();
        this.content = source.getContent();
        this.createAt = source.getCreateAt();
    }

    public Long getSeq() {
        return seq;
    }

    public Long getProductId() {
        return productId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
