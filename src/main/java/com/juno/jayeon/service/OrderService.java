package com.juno.jayeon.service;

import com.juno.jayeon.domain.dto.GetOrderDto;
import com.juno.jayeon.domain.dto.OrderDto;
import com.juno.jayeon.domain.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    List<GetOrderDto> findAll() throws Exception;

    OrderResponseDto save(OrderDto orderDto) throws Exception;

    OrderResponseDto update(OrderDto orderDto) throws Exception;

    OrderResponseDto delete(Long idx) throws Exception;
}
