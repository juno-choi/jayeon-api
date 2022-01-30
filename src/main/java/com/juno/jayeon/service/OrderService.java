package com.juno.jayeon.service;

import com.juno.jayeon.domain.dto.OrderDto;
import com.juno.jayeon.domain.dto.OrderResponseDto;
import com.juno.jayeon.domain.entity.Order;

import java.util.List;

public interface OrderService {
    public List<Order> findAll() throws Exception;

    OrderResponseDto save(OrderDto orderDto) throws Exception;
}
