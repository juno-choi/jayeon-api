package com.juno.jayeon.domain.dto;

import com.juno.jayeon.domain.entity.OrderStatus;
import lombok.Data;

@Data
public class SearchDto {
    private String sDate;
    private String eDate;
    private String buyer;
    private OrderStatus orderStatus;
}
