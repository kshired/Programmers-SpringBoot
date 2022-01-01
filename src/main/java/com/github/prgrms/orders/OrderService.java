package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Orders> findById(Long id);
    List<Orders> findAll(long offset, int size);
    boolean updateById(Orders orders, Long id);
}
