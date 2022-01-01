package com.github.prgrms.orders;

import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.products.ProductService;
import com.github.prgrms.security.JwtAuthentication;
import com.github.prgrms.utils.ApiUtils;
import com.github.prgrms.utils.ApiUtils.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static com.github.prgrms.utils.ApiUtils.error;
import static com.github.prgrms.utils.ApiUtils.success;

@RestController
@RequestMapping("api/orders")
public class ReviewRestController {
    // TODO review 메소드 구현이 필요합니다.
    private final OrderService orderService;
    private final ReviewService reviewService;
    private final ProductService productService;

    public ReviewRestController(OrderService orderService, ReviewService reviewService, ProductService productService) {
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.productService = productService;
    }

    @PostMapping("/{id}/review")
    public ApiResult<?> review(@PathVariable("id") Long id,
                               @Valid @RequestBody ReviewRequest request,
                               @AuthenticationPrincipal JwtAuthentication authentication) {
        Long userId = authentication.id;
        Orders order = orderService.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found order for " + id));
        String content = request.getContent();

        if (content.length() > 1000) {
            throw new IllegalStateException("Could not write review more than 1000 characters");
        }

        if (order.getState() != OrderStatus.COMPLETED) {
            throw new IllegalStateException("Could not write review for order " + id + " because state(REQUESTED) is not allowed");
        }

        if (order.getReview() != null) {
            throw new IllegalStateException("Could not write review for order " + id + " because have already written");
        }

        productService.updateProductReviewCount(order.getProductSeq());

        Review review = new Review(userId, order.getProductSeq(), content, LocalDateTime.now());
        Long reviewId = reviewService.createReview(review, id);

        review.setSeq(reviewId);

        return success(new ReviewDto(review));
    }


}