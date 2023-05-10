package com.example.truck.business.controller.external;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.truck.business.dto.api.GetOrdersResponseDto;
import com.example.truck.business.dto.api.SetDriverToOrderRequestDto;
import com.example.truck.business.dto.api.UpdateOrderStatusRequestDto;
import com.example.truck.business.service.api.OrderService;

import static io.tesler.core.config.properties.APIProperties.TESLER_API_PATH_SPEL;

@RestController
@RequiredArgsConstructor
@RequestMapping(TESLER_API_PATH_SPEL + "${app.api-path}" + "/order")
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/updateOrderStatus")
	public ResponseEntity<Void> updateOrderStatus(@RequestBody UpdateOrderStatusRequestDto dto) {
		orderService.updateOrderStatus(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/setDriverToOrder")
	public ResponseEntity<Void> setDriverToOrder(@RequestBody SetDriverToOrderRequestDto dto) {
		orderService.setDriverToOrder(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/getOrders")
	public ResponseEntity<List<GetOrdersResponseDto>> getOrders(@RequestParam Integer page, @RequestParam Integer size) {
		return new ResponseEntity<>(orderService.getOrders(page, size), HttpStatus.OK);
	}

}
