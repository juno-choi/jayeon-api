package com.juno.jayeon.domain.dto;

import com.juno.jayeon.domain.entity.ItemOption;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GetItemsDto {
    private Long idx;
    private String name;
    private List<GetItemOptionDto> options;
    private Long price;

    @Builder
    public GetItemsDto(Long idx, String name, List<GetItemOptionDto> options, Long price) {
        this.idx = idx;
        this.name = name;
        this.options = options;
        this.price = price;
    }
}
