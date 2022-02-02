package com.juno.jayeon.controller;

import com.juno.jayeon.domain.dto.GetOrderDto;
import com.juno.jayeon.domain.dto.OrderDto;
import com.juno.jayeon.domain.dto.OrderResponseDto;
import com.juno.jayeon.domain.dto.SearchDto;
import com.juno.jayeon.domain.dto.api.CommonV1;
import com.juno.jayeon.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<CommonV1> getOrders() throws Exception{
        List<GetOrderDto> orders = orderService.findAll();
        CommonV1 body = new CommonV1("200", "정상", orders);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/status")
    public ResponseEntity<CommonV1> putOrdersStatus(@RequestBody OrderDto orderDto) throws Exception{
        OrderResponseDto update = orderService.update(orderDto);
        CommonV1 body = new CommonV1("200", "정상", update);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<CommonV1> deleteOrder(@PathVariable(value = "idx") Long idx) throws Exception{
        OrderResponseDto delete = orderService.delete(idx);
        CommonV1 body = new CommonV1("200", "정상", delete);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/search")
    public ResponseEntity<CommonV1> searchOrder(SearchDto searchDto) throws Exception{
        List<GetOrderDto> search = orderService.search(searchDto);
        CommonV1 body = new CommonV1("200", "정상", search);
        return ResponseEntity.ok(body);
    }
}
