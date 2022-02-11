package com.juno.jayeon.service;

import com.juno.jayeon.controller.dto.GetItemsDto;

import java.util.List;

public interface ItemService {
    public List<GetItemsDto> findAll() throws Exception;
}
