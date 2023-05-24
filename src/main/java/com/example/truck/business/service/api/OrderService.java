package com.example.truck.business.service.api;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.truck.business.dto.api.GetOrdersResponseDto;
import com.example.truck.business.dto.api.SetDriverToOrderRequestDto;
import com.example.truck.business.dto.api.UpdateOrderStatusRequestDto;
import com.example.truck.business.repository.DriverRepository;
import com.example.truck.business.repository.OrderIssueRepository;
import com.example.truck.business.service.api.mapper.OrderDtoMapper;

import static io.tesler.model.core.entity.BaseEntity_.createdDate;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderIssueRepository orderIssueRepository;

	private final DriverRepository driverRepository;

	private static final Sort GET_ORDER_LIST_SORT = Sort.by(createdDate.getName()).ascending();

	public void setDriverToOrder(SetDriverToOrderRequestDto dto) {
		Optional.ofNullable(dto.getOrderId())
				.map(orderIssueRepository::getById)
				.ifPresent(
						order -> {
							order.setDriver(Optional.ofNullable(dto.getDriverId())
									.map(driverRepository::getById)
									.orElse(null));
						}
				);
	}

	public void updateOrderStatus(UpdateOrderStatusRequestDto dto) {
		if (dto.getOrderStatus() != null) {
			Optional.ofNullable(dto.getOrderId())
					.map(orderIssueRepository::getById)
					.ifPresent(order -> order.setStatusCd(dto.getOrderStatus()));
		}
	}

	public List<GetOrdersResponseDto> getOrders(final Integer page, final Integer size) {
		return orderIssueRepository.findAll(
						OrderIssueRepository.findAllAvailableOrders(),
						PageRequest.of(page, size, GET_ORDER_LIST_SORT)
				).stream()
				.map(OrderDtoMapper::mapEntityToDto)
				.toList();
	}

}
