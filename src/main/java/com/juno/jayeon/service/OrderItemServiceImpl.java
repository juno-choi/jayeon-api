package com.juno.jayeon.service;

import com.juno.jayeon.domain.entity.OrderItem;
import com.juno.jayeon.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem orderItem) throws Exception {
        return null;
    }
}
