package com.example.truck.business.service.api.mapper;

import lombok.experimental.UtilityClass;

import com.example.truck.business.dto.api.GetOrdersResponseDto;
import com.example.truck.business.dto.api.GetOrdersResponseDto.OrderDto;
import com.example.truck.business.dto.api.GetOrdersResponseDto.OrderDto.CargoDto;
import com.example.truck.business.repository.entity.Cargo;
import com.example.truck.business.repository.entity.OrderIssue;

@UtilityClass
public class OrderDtoMapper {

	public GetOrdersResponseDto mapEntityToDto(final OrderIssue entity) {
		return GetOrdersResponseDto.builder()
				.order(buildOrderDtoFromEntity(entity))
				.build();
	}

	private OrderDto buildOrderDtoFromEntity(final OrderIssue entity) {
		if (entity != null) {
			return OrderDto.builder()
					.id(entity.getId())
					.createdDate(entity.getCreatedDate())
					.name(entity.getName())
					.price(entity.getPrice())
					.typeCd(entity.getTypeCd())
					.statusCd(entity.getStatusCd())
					.completionDate(entity.getCompletionDate())
					.departurePoint(entity.getDeparturePoint())
					.destinationPoint(entity.getDestinationPoint())
					.comment(entity.getComment())
					.cargo(buildCargoDtoFromEntity(entity.getCargo()))
					.build();
		}
		return null;
	}

	private CargoDto buildCargoDtoFromEntity(final Cargo entity) {
		if (entity != null) {
			return CargoDto.builder()
					.name(entity.getName())
					.typeCd(entity.getTypeCd())
					.dimensionWidth(entity.getDimensionWidth())
					.dimensionLength(entity.getDimensionLength())
					.weight(entity.getWeight())
					.build();
		}
		return null;
	}

}
