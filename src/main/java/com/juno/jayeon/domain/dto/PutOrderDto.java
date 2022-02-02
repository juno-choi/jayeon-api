package com.juno.jayeon.domain.dto;

import com.juno.jayeon.domain.entity.OrderStatus;
import lombok.Data;

@Data
public class PutOrderDto {
    private long idx;
    private OrderStatus orderStatus;
}
