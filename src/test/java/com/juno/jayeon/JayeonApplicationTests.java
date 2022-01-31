package com.juno.jayeon;

import com.juno.jayeon.domain.dto.GetItemsDto;
import com.juno.jayeon.domain.entity.Item;
import com.juno.jayeon.domain.entity.ItemOption;
import com.juno.jayeon.domain.entity.Order;
import com.juno.jayeon.domain.entity.OrderItem;
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

	@Test
	void contextLoads() {
	}

	@Test
	void itemPrint() throws Exception{
		List<Order> all = orderService.findAll();
		for (Order order : all) {
			System.out.println("order.getIdx() = " + order.getIdx());
			List<OrderItem> itemList = order.getItemList();
			for (OrderItem orderItem : itemList) {
				System.out.println("orderItem.getItem() = " + orderItem.getItem());
				System.out.println("orderIte = " + orderItem.getOption());
			}
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
