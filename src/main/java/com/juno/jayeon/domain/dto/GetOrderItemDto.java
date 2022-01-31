package com.juno.jayeon.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class GetOrderItemDto {
    private Long idx;
    private String item;
    private String option;
    private int kg;
    private long price;
    private int ea;

    @Builder
    public GetOrderItemDto(Long idx, String item, String option, int kg, long price, int ea) {
        this.idx = idx;
        this.item = item;
        this.option = option;
        this.kg = kg;
        this.price = price;
        this.ea = ea;
    }
}
