package com.juno.jayeon.service;

import com.google.gson.*;
import com.juno.jayeon.domain.dto.OrderDto;
import com.juno.jayeon.domain.dto.OrderResponseDto;
import com.juno.jayeon.domain.entity.Order;
import com.juno.jayeon.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() throws Exception {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public OrderResponseDto save(OrderDto orderDto) throws Exception {
        JsonParser parser = new JsonParser();
        String jsonStr = orderDto.getOrder();
        JsonElement parse = parser.parse(jsonStr);
        JsonArray jsonArray = parse.getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            Map<String, Object> map = new HashMap<>();
            Gson gson = new Gson();
            map = gson.fromJson(jsonElement, map.getClass());
            System.out.println("map = " + map.toString());
            System.out.println("item idx = " + map.get("item"));
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrder_idx(1L);
        return orderResponseDto;
    }
}
