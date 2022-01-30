package com.juno.jayeon.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class GetItemOptionDto {
    private long idx;
    private String name;
    private long price;

    @Builder
    public GetItemOptionDto(long idx, String name, long price) {
        this.idx = idx;
        this.name = name;
        this.price = price;
    }
}
