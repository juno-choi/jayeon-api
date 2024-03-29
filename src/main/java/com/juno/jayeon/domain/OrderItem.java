package com.juno.jayeon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_idx")
    private Long idx;

    private Long item;
    private Long option;
    private int ea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_idx")
    private Order order;

    @Builder
    public OrderItem(Long item, Long option, int ea, Order order) {
        this.item = item;
        this.option = option;
        this.ea = ea;
        this.order = order;
    }
}
