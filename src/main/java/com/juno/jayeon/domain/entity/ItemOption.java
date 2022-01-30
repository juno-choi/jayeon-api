package com.juno.jayeon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class ItemOption {
    @Id
    @GeneratedValue
    @Column(name = "item_option_idx")
    private Long idx;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_idx")
    private Item item;

    private Long price;
}
