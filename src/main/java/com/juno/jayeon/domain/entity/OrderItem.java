package com.juno.jayeon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_idx")
    private Order order;

    private String name;
    @Column(name = "item_price")
    private Long itemPrice;
    private String option;
    @Column(name = "option_price")
    private Long optionPrice;
}
