package com.juno.jayeon.domain.dto.api;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonV1<T> {
    private String code;
    private String msg;
    private T data;

    @Builder
    public CommonV1(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
