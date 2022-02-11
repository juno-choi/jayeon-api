package com.juno.jayeon.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class GetItemOptionDto {
    private long idx;
    private int kg;
    private String name;
    private long price;

    @Builder
    public GetItemOptionDto(long idx, int kg, String name, long price) {
        this.idx = idx;
        this.kg = kg;
        this.name = name;
        this.price = price;
    }
}
