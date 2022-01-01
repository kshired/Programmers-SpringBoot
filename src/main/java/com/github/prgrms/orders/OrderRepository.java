package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Orders> findAll(long offset, int size);
    Optional<Orders> findOrderById(Long id);
    void updateOrder(Orders order, Long id);
}
