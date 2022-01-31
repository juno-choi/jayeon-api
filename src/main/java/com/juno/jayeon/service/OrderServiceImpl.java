package com.juno.jayeon.service;

import com.google.gson.*;
import com.juno.jayeon.domain.dto.GetOrderDto;
import com.juno.jayeon.domain.dto.GetOrderItemDto;
import com.juno.jayeon.domain.dto.OrderDto;
import com.juno.jayeon.domain.dto.OrderResponseDto;
import com.juno.jayeon.domain.entity.Order;
import com.juno.jayeon.domain.entity.OrderItem;
import com.juno.jayeon.repository.OrderItemRepository;
import com.juno.jayeon.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<GetOrderDto> findAll() throws Exception {
        List<Order> orders = orderRepository.findAll();
        List<GetOrderDto> ordersList = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> itemList = order.getItemList();
            List<GetOrderItemDto> orderItemList = new ArrayList<>();

            for (OrderItem orderItem : itemList) {
                GetOrderItemDto goid = GetOrderItemDto.builder()
                        .idx(orderItem.getIdx())
                        .item(orderItem.getItem())
                        .ea(orderItem.getEa())
                        .option(orderItem.getOption())
                        .build();
                orderItemList.add(goid);
            }

            GetOrderDto god = GetOrderDto.builder()
                    .idx(order.getIdx())
                    .buyer(order.getBuyer())
                    .recipient(order.getRecipient())
                    .itemList(orderItemList)
                    .tel1(order.getTel1())
                    .tel2(order.getTel2())
                    .tel3(order.getTel3())
                    .post1(order.getPost1())
                    .post2(order.getPost2())
                    .post3(order.getPost3())
                    .request(order.getRequest())
                    .status(order.getStatus())
                    .build();

            ordersList.add(god);
        }

        return ordersList;
    }

    @Override
    @Transactional
    public OrderResponseDto save(OrderDto orderDto) throws Exception {
        JsonParser parser = new JsonParser();
        String jsonStr = orderDto.getOrder();
        JsonElement parse = parser.parse(jsonStr);
        JsonArray jsonArray = parse.getAsJsonArray();

        Order order = Order.builder()
                .buyer(orderDto.getBuyer())
                .recipient(orderDto.getRecipient())
                .tel1(orderDto.getTel1())
                .tel2(orderDto.getTel2())
                .tel3(orderDto.getTel3())
                .post1(orderDto.getPost1())
                .post2(orderDto.getPost2())
                .post3(orderDto.getPost3())
                .request(orderDto.getRequest())
                .build();
        Long orderIdx = order.getIdx();

        orderRepository.save(order);

        for (JsonElement jsonElement : jsonArray) {
            Map<String, Object> map = new HashMap<>();
            Gson gson = new Gson();
            map = gson.fromJson(jsonElement, map.getClass());
            System.out.println("map = " + map.toString());
            System.out.println("item idx = " + map.get("item"));

            String item = map.get("item").toString();
            String option = map.get("option").toString();
            int ea = Integer.valueOf(map.get("ea").toString());

            OrderItem orderItem = OrderItem.builder()
                .item(item)
                .option(option)
                .ea(ea)
                .order(order)
                .build();

            orderItemRepository.save(orderItem);
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrder_idx(orderIdx);  //주문번호 반환
        return orderResponseDto;
    }
}
