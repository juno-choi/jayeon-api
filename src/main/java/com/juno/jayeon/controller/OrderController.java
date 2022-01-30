package com.juno.jayeon.controller;

import com.juno.jayeon.domain.dto.OrderDto;
import com.juno.jayeon.domain.dto.OrderResponseDto;
import com.juno.jayeon.domain.dto.api.CommonV1;
import com.juno.jayeon.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<CommonV1> putOrders(@RequestBody OrderDto orderDto) throws Exception{
        OrderResponseDto ord = orderService.save(orderDto);
        CommonV1 body = new CommonV1("200", "정상", ord);
        return ResponseEntity.ok().body(body);
    }
}
