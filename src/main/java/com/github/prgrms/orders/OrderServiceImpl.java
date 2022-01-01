package com.github.prgrms.orders;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public List<Orders> findAll(long offset, int size) {
        return orderRepository.findAll(offset, size);
    }

    @Override
    public boolean updateById(Orders orders, Long id) {
        orderRepository.updateOrder(orders, id);
        return true;
    }
}

