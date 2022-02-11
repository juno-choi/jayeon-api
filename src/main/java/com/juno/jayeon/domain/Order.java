package com.juno.jayeon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_idx")
    private Long idx;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> itemList = new ArrayList<>();

    private String buyer;
    private String buyerTel1;
    private String buyerTel2;
    private String buyerTel3;
    private String recipient;
    private String recipientTel1;
    private String recipientTel2;
    private String recipientTel3;
    private String post1;
    private String post2;
    private String post3;
    private OrderStatus status;
    private String request;
    @Column(name = "reg_date")
    private String regDate;

    @Builder
    public Order(String buyer, String recipient, String buyerTel1, String buyerTel2, String buyerTel3, String recipientTel1, String recipientTel2, String recipientTel3, String post1, String post2, String post3, String request, OrderStatus status, String regDate) {
        this.buyer = buyer;
        this.buyerTel1 = buyerTel1;
        this.buyerTel2 = buyerTel2;
        this.buyerTel3 = buyerTel3;
        this.recipient = recipient;
        this.recipientTel1 = recipientTel1;
        this.recipientTel2 = recipientTel2;
        this.recipientTel3 = recipientTel3;
        this.post1 = post1;
        this.post2 = post2;
        this.post3 = post3;
        this.request = request;
        this.status = status;
        this.regDate = regDate;
    }

    public Order changeStatus(OrderStatus status){
        this.status = status;
        return this;
    }
}
