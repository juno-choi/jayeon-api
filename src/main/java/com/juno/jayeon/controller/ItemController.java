package com.juno.jayeon.controller;

import com.juno.jayeon.domain.dto.GetItemsDto;
import com.juno.jayeon.domain.dto.api.CommonV1;
import com.juno.jayeon.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/items")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<CommonV1> getItems() throws Exception{
        List<GetItemsDto> items = itemService.findAll();
        CommonV1 body = new CommonV1("200","정상",items);
        return ResponseEntity.ok().body(body);
    }
}
