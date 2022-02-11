package com.juno.jayeon.repository;

import com.juno.jayeon.controller.dto.SearchDto;
import com.juno.jayeon.domain.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> search(SearchDto searchDto);
}
