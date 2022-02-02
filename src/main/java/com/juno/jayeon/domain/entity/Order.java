package com.juno.jayeon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    private OrderStatus status;
    private String request;
    @Column(name = "reg_date")
    private String regDate;

    @Builder
    public Order(String buyer, String recipient, String tel1, String tel2, String tel3, String post1, String post2, String post3, String request, OrderStatus status, String regDate) {
        this.buyer = buyer;
        this.recipient = recipient;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
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
