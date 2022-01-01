package com.github.prgrms.orders;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createReview(Review review, Long orderId) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement pstmt = connection.prepareStatement(
                                "INSERT INTO reviews(user_seq,product_seq,content) VALUES (?,?,?)",
                                new String[]{"seq"}
                        );
                        pstmt.setLong(1,review.getUserSeq());
                        pstmt.setLong(2,review.getProductSeq());
                        pstmt.setString(3,review.getContent());
                        return pstmt;
                    }
                }, generatedKeyHolder
        );

        long review_id = generatedKeyHolder.getKey().longValue();

        jdbcTemplate.update(
                "UPDATE orders set review_seq=? WHERE seq=?",
                review_id,
                orderId
        );

        return review_id;
    }
}
