package com.juno.jayeon.domain.dto.api;

import lombok.Getter;

@Getter
public class ErrorV1<T> {
    private T errors;
}
