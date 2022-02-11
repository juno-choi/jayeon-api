package com.juno.jayeon.controller.dto;

import com.juno.jayeon.domain.OrderStatus;
import lombok.Data;

@Data
public class SearchDto {
    private String sDate;
    private String eDate;
    private String buyer;
    private OrderStatus orderStatus;
}
