package com.juno.jayeon.repository;

import com.juno.jayeon.domain.dto.SearchDto;
import com.juno.jayeon.domain.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> search(SearchDto searchDto);
}
