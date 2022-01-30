package com.juno.jayeon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_idx")
    private Long idx;

    private String name;

    @OneToMany(mappedBy = "item")
    private List<ItemOption> option = new ArrayList<>();
    private Long price;
}
