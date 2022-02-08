package com.juno.jayeon.domain.dto;

import com.juno.jayeon.domain.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {

    private long idx;
    private OrderStatus orderStatus;

    //상품 정보
    private String order;
    
    //주문자 정보
    private String buyer;
    private String buyerTel1;
    private String buyerTel2;
    private String buyerTel3;
    private String recipient;
    private String recipientTel1;
    private String recipientTel2;
    private String recipientTel3;
    private String post1;
    private String post2;
    private String post3;

    //기타 요청
    private String request;
}
