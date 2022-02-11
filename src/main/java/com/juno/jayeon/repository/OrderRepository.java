package com.juno.jayeon.repository;

import com.juno.jayeon.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
