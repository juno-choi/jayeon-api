package com.juno.jayeon.domain.entity;

import lombok.AllArgsConstructor;

/**
 * 결제 상태 enum
 */
@AllArgsConstructor
public enum OrderStatus {
    BEFORE("BEFORE"),
    DEPOSIT("DEPOSIT"),
    COMPLETE("COMPLETE")
    ;
    private String value;
}
