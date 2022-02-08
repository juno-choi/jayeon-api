package com.juno.jayeon.service;

import com.google.gson.*;
import com.juno.jayeon.domain.dto.*;
import com.juno.jayeon.domain.entity.*;
import com.juno.jayeon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final OrderRepositoryCustom orderRepositoryCustom;

    @Override
    public List<GetOrderDto> findAll() throws Exception {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "idx"));
        return getGetOrderDto(orders);
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
                .buyerTel1(orderDto.getBuyerTel1())
                .buyerTel2(orderDto.getBuyerTel2())
                .buyerTel3(orderDto.getBuyerTel3())
                .recipient(orderDto.getRecipient())
                .recipientTel1(orderDto.getRecipientTel1())
                .recipientTel2(orderDto.getRecipientTel2())
                .recipientTel3(orderDto.getRecipientTel3())
                .post1(orderDto.getPost1())
                .post2(orderDto.getPost2())
                .post3(orderDto.getPost3())
                .request(orderDto.getRequest())
                .status(OrderStatus.BEFORE)
                .regDate(LocalDateTime.now().toString())
                .build();

        Order save = orderRepository.save(order);
        Long orderIdx = save.getIdx();

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
                .item(Long.valueOf(item))
                .option(Long.valueOf(option))
                .ea(ea)
                .order(order)
                .build();

            orderItemRepository.save(orderItem);
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderIdx(orderIdx);  //주문번호 반환
        return orderResponseDto;
    }

    @Override
    @Transactional
    public OrderResponseDto update(OrderDto orderDto) throws Exception{
        OrderResponseDto ord = new OrderResponseDto();
        long idx = orderDto.getIdx();
        OrderStatus orderStatus = orderDto.getOrderStatus();
        OrderStatus afterOrderStatus = null;
        if(orderStatus == OrderStatus.BEFORE){
            afterOrderStatus = OrderStatus.DEPOSIT;
        }else if(orderStatus == OrderStatus.DEPOSIT){
            afterOrderStatus = OrderStatus.COMPLETE;
        }

        Order order = orderRepository.findById(idx).get();
        order.changeStatus(afterOrderStatus);

        ord.setOrderIdx(idx);
        return ord;
    }

    @Override
    @Transactional
    public OrderResponseDto delete(Long idx) throws Exception{
        orderRepository.deleteById(idx);
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderIdx(idx);
        return orderResponseDto;
    }

    @Override
    public List<GetOrderDto> search(SearchDto searchDto) throws Exception {
        List<Order> orders = orderRepositoryCustom.search(searchDto);
        return getGetOrderDto(orders);
    }

    private List<GetOrderDto> getGetOrderDto(List<Order> orders) {
        List<GetOrderDto> ordersList = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> itemList = order.getItemList();
            List<GetOrderItemDto> orderItemList = new ArrayList<>();
            Long price = 0L;

            for (OrderItem orderItem : itemList) {

                Optional<Item> itemOptional = itemRepository.findById(orderItem.getItem());
                Item item = itemOptional.get();
                String itemName = item.getName();
                long itemPrice = item.getPrice();

                Optional<ItemOption> itemOptionOptional = itemOptionRepository.findById(orderItem.getOption());
                ItemOption itemOption = itemOptionOptional.get();
                String optionName = itemOption.getName();
                int kg = itemOption.getKg();
                long optionPrice = itemOption.getPrice();
                long ea = orderItem.getEa();
                price += (itemPrice*ea) + (optionPrice*ea);
                long itemOptionPrice = itemPrice + optionPrice;

                GetOrderItemDto goid = GetOrderItemDto.builder()
                        .idx(orderItem.getIdx())
                        .item(itemName)
                        .option(optionName)
                        .kg(kg)
                        .ea((int)ea)
                        .price(itemOptionPrice)
                        .build();
                orderItemList.add(goid);
            }

            LocalDateTime parse = LocalDateTime.parse(order.getRegDate());
            String regDate = parse.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            OrderStatus status = order.getStatus();

            GetOrderDto god = GetOrderDto.builder()
                    .idx(order.getIdx())
                    .buyer(order.getBuyer())
                    .buyerTel1(order.getBuyerTel1())
                    .buyerTel2(order.getBuyerTel2())
                    .buyerTel3(order.getBuyerTel3())
                    .itemList(orderItemList)
                    .recipient(order.getRecipient())
                    .recipientTel1(order.getRecipientTel1())
                    .recipientTel2(order.getRecipientTel2())
                    .recipientTel3(order.getRecipientTel3())
                    .post1(order.getPost1())
                    .post2(order.getPost2())
                    .post3(order.getPost3())
                    .request(order.getRequest())
                    .status(status)
                    .regDate(regDate)
                    .price(price)
                    .build();

            ordersList.add(god);
        }

        return ordersList;
    }
}
