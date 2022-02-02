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
    private String recipient;
    private String tel1;
    private String tel2;
    private String tel3;
    private String post1;
    private String post2;
    private String post3;

    //기타 요청
    private String request;
}
