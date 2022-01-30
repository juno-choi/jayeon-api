package com.juno.jayeon.domain.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonErrorV1 {
    private String msg;
    private String detail = "";

    public CommonErrorV1(String msg) {
        this.msg = msg;
    }
}
