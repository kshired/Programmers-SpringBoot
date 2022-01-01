package com.github.prgrms.orders;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ReviewRepository {
    Long createReview(Review review, Long orderId);
}
