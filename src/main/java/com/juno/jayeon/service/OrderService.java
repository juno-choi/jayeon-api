package com.juno.jayeon.service;

import com.juno.jayeon.service.dto.GetOrderDto;
import com.juno.jayeon.controller.dto.OrderDto;
import com.juno.jayeon.controller.dto.OrderResponseDto;
import com.juno.jayeon.controller.dto.SearchDto;

import java.util.List;

public interface OrderService {
    List<GetOrderDto> findAll() throws Exception;

    OrderResponseDto save(OrderDto orderDto) throws Exception;

    OrderResponseDto update(OrderDto orderDto) throws Exception;

    OrderResponseDto delete(Long idx) throws Exception;

    List<GetOrderDto> search(SearchDto searchDto) throws Exception;
}
