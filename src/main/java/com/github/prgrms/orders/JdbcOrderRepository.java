package com.github.prgrms.orders;

import com.github.prgrms.utils.DateTimeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Orders> findAll(long offset, int size) {
        List<Orders> orders = jdbcTemplate.query(
                "SELECT * FROM orders ORDER BY seq DESC LIMIT ? OFFSET ?",
                (rs, rowNum) -> new Orders(
                        rs.getLong("seq"),
                        rs.getLong("user_seq"),
                        rs.getLong("product_seq"),
                        rs.getLong("review_seq"),
                        OrderStatus.valueOf(rs.getString("state")),
                        rs.getString("request_msg"),
                        rs.getString("reject_msg"),
                        DateTimeUtils.dateTimeOf(rs.getTimestamp("completed_at")),
                        DateTimeUtils.dateTimeOf(rs.getTimestamp("rejected_at")),
                        DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at"))
                ),
                size,
                offset
        );

        return orders.stream().map((r) -> {
            List<Review> reviews = jdbcTemplate.query(
                    "SELECT * FROM reviews WHERE product_seq=?",
                    (rs, rowNum) -> new Review(
                            rs.getLong("seq"),
                            rs.getLong("user_seq"),
                            rs.getLong("product_seq"),
                            rs.getString("content"),
                            DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at"))
                    ),
                    r.getProductSeq()
            );
            r.setReview(reviews.isEmpty() ? null : reviews.get(0));
            return r;
        }).collect(Collectors.toList());

    }


    @Override
    public Optional<Orders> findOrderById(Long id) {
        List<Orders> result = jdbcTemplate.query(
                "SELECT * FROM orders where seq=?",
                (rs, rowNum) -> new Orders(
                        rs.getLong("seq"),
                        rs.getLong("user_seq"),
                        rs.getLong("product_seq"),
                        rs.getLong("review_seq"),
                        OrderStatus.valueOf(rs.getString("state")),
                        rs.getString("request_msg"),
                        rs.getString("reject_msg"),
                        DateTimeUtils.dateTimeOf(rs.getTimestamp("completed_at")),
                        DateTimeUtils.dateTimeOf(rs.getTimestamp("rejected_at")),
                        DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at"))
                ),
                id
        );

        if(result.isEmpty()){
            return null;
        }


        List<Review> reviews = jdbcTemplate.query(
                "SELECT * FROM reviews WHERE seq=?",
                (rs, rowNum) -> new Review(
                        rs.getLong("seq"),
                        rs.getLong("user_seq"),
                        rs.getLong("product_seq"),
                        rs.getString("content"),
                        DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at"))
                ),
                result.get(0).getReviewSeq()
        );


        result.get(0).setReview(reviews.isEmpty() ? null : reviews.get(0));

        return ofNullable(result.get(0));
    }

    @Override
    public void updateOrder(Orders order, Long id) {
        jdbcTemplate.update(
                "UPDATE orders set state=?, rejected_at=?, completed_at=?, reject_msg=? WHERE seq=?",
                order.getState().toString(),
                DateTimeUtils.timestampOf(order.getRejectedAt()),
                DateTimeUtils.timestampOf(order.getCompletedAt()),
                order.getRejectMsg(),
                id
        );
    }
}
