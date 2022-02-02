package com.juno.jayeon.domain.dto;

import com.juno.jayeon.domain.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GetOrderDto {

    private Long idx;

    private List<GetOrderItemDto> itemList;

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
    private String request;
    private OrderStatus status;
    private String regDate;
    private Long price;

    @Builder
    public GetOrderDto(Long idx, List<GetOrderItemDto> itemList, String buyer, String buyerTel1, String buyerTel2, String buyerTel3, String recipient, String recipientTel1, String recipientTel2, String recipientTel3, String post1, String post2, String post3, String request, OrderStatus status, String regDate, Long price) {
        this.idx = idx;
        this.itemList = itemList;
        this.buyer = buyer;
        this.buyerTel1 = buyerTel1;
        this.buyerTel2 = buyerTel2;
        this.buyerTel3 = buyerTel3;
        this.recipient = recipient;
        this.recipientTel1 = recipientTel1;
        this.recipientTel2 = recipientTel2;
        this.recipientTel3 = recipientTel3;
        this.post1 = post1;
        this.post2 = post2;
        this.post3 = post3;
        this.request = request;
        this.status = status;
        this.regDate = regDate;
        this.price = price;
    }
}
