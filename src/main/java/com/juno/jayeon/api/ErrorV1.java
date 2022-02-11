package com.juno.jayeon.api;

import lombok.Getter;

@Getter
public class ErrorV1<T> {
    private T errors;
}
