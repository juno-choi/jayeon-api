package com.juno.jayeon.service;

import com.juno.jayeon.domain.dto.GetItemOptionDto;
import com.juno.jayeon.domain.dto.GetItemsDto;
import com.juno.jayeon.domain.entity.Item;
import com.juno.jayeon.domain.entity.ItemOption;
import com.juno.jayeon.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<GetItemsDto> findAll() {
        List<Item> items = itemRepository.findAll();
        List<GetItemsDto> result = new ArrayList<>();

        for (Item item : items) {
            List<ItemOption> options = item.getOption();
            List<GetItemOptionDto> dtoOptions = new ArrayList<>();
            for (ItemOption o : options) {
                GetItemOptionDto getItemOptionDto = GetItemOptionDto.builder()
                        .idx(o.getIdx())
                        .kg(o.getKg())
                        .name(o.getName())
                        .price(o.getPrice())
                        .build();
                dtoOptions.add(getItemOptionDto);
            }

            GetItemsDto gid = GetItemsDto.builder()
                    .idx(item.getIdx())
                    .name(item.getName())
                    .price(item.getPrice())
                    .options(dtoOptions)
                    .build();
            result.add(gid);
        }
        return result;
    }
}
