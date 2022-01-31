package com.juno.jayeon.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GetOrderDto {

    private Long idx;

    private List<GetOrderItemDto> itemList;

    private String buyer;
    private String recipient;
    private String tel1;
    private String tel2;
    private String tel3;
    private String post1;
    private String post2;
    private String post3;
    private String request;
    private String status;
    private String regDate;
    private Long price;

    @Builder
    public GetOrderDto(Long idx, List<GetOrderItemDto> itemList, String buyer, String recipient, String tel1, String tel2, String tel3, String post1, String post2, String post3, String request, String status, String regDate, Long price) {
        this.idx = idx;
        this.itemList = itemList;
        this.buyer = buyer;
        this.recipient = recipient;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
        this.post1 = post1;
        this.post2 = post2;
        this.post3 = post3;
        this.request = request;
        this.status = status;
        this.regDate = regDate;
        this.price = price;
    }
}
