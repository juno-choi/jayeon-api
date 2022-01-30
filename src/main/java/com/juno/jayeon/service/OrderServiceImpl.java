package com.juno.jayeon.service;

import com.google.gson.*;
import com.juno.jayeon.domain.dto.OrderDto;
import com.juno.jayeon.domain.dto.OrderResponseDto;
import com.juno.jayeon.domain.entity.Order;
import com.juno.jayeon.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        String jsonStr = orderDto.getJson();
        JsonElement parse = parser.parse(jsonStr);
        JsonArray jsonArray = parse.getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            System.out.println("jsonElement = " + jsonElement);
            JsonElement item = jsonElement.getAsJsonObject().get("item");
            System.out.println("item = " + item);
        }

        return null;
    }
}
