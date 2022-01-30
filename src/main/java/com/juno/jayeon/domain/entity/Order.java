package com.juno.jayeon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_idx")
    private Long idx;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> itemList = new ArrayList<>();

    private String buyer;
    private String recipient;
    private String tel1;
    private String tel2;
    private String tel3;
    private String post1;
    private String post2;
    private String post3;
    private String request;
}
