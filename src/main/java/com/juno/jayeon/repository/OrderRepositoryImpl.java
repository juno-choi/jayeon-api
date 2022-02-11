package com.juno.jayeon.repository;

import com.juno.jayeon.controller.dto.SearchDto;
import com.juno.jayeon.domain.Order;
import com.juno.jayeon.domain.QOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory query;

    QOrder order = QOrder.order;

    @Override
    public List<Order> search(SearchDto searchDto) {
        BooleanBuilder builder = new BooleanBuilder();
        if(searchDto.getBuyer() != null){
            builder.and(order.buyer.eq(searchDto.getBuyer()));
        }
        if(searchDto.getOrderStatus() != null){
            builder.and(order.status.eq(searchDto.getOrderStatus()));
        }
        if(searchDto.getSDate() != null){
            if(searchDto.getEDate() != null){
                builder.and(order.regDate.between(searchDto.getSDate(), searchDto.getEDate()));
            }else{
                builder.and(order.regDate.between(searchDto.getSDate(), "2999-12-31"));
            }
        }

        List<Order> fetch = query.selectFrom(order)
                .where(builder)
                .orderBy(order.idx.desc())
                .fetch();

        return fetch;
    }
}
