package com.juno.jayeon;

import com.juno.jayeon.domain.dto.GetItemsDto;
import com.juno.jayeon.domain.dto.GetOrderDto;
import com.juno.jayeon.domain.dto.OrderResponseDto;
import com.juno.jayeon.domain.dto.SearchDto;
import com.juno.jayeon.domain.entity.*;
import com.juno.jayeon.repository.OrderRepositoryCustom;
import com.juno.jayeon.service.ItemService;
import com.juno.jayeon.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional(readOnly = true)
class JayeonApplicationTests {

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepositoryCustom orderRepositoryCustom;

	@Test
	void contextLoads() {
	}

	@Test
	void 조건_검색_테스트() throws Exception{
		SearchDto searchDto = new SearchDto();
		searchDto.setBuyer("테스터");
		searchDto.setSDate("2022-01-01");
		searchDto.setEDate("2022-01-31");
		searchDto.setOrderStatus(OrderStatus.BEFORE);
		List<GetOrderDto> search = orderService.search(searchDto);
		for (GetOrderDto getOrderDto : search) {
			System.out.println("getOrderDto.getBuyer() = " + getOrderDto.getBuyer());
		}

	}

	@Test
	void orderPrint() throws Exception{
		List<GetItemsDto> all = itemService.findAll();
		for (GetItemsDto getItemsDto : all) {
			System.out.println("getItemsDto = " + getItemsDto.getName());
		}
	}
}
