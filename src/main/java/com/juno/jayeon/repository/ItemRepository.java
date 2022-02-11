package com.juno.jayeon.repository;

import com.juno.jayeon.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
