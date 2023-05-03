package com.example.truck.business.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.DictionaryType;
import com.example.truck.business.repository.entity.Driver;
import com.example.truck.business.repository.entity.OrderIssue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tesler.api.data.dto.DataResponseDTO;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderIssueDTO extends DataResponseDTO {

	private String name;

	private Long price;

	@DictionaryType(AppDictionaryType.ORDER_TYPE_CD)
	private String typeCd;

	@DictionaryType(AppDictionaryType.ORDER_STATUS_CD)
	private String statusCd;

	private Long driverId;

	private String driverFio;

	private LocalDateTime completionDate;

	private String departurePoint;

	private String destinationPoint;

	private String comment;

	public OrderIssueDTO(final OrderIssue entity) {
		this.id = String.valueOf(entity.getId());
		this.name = entity.getName();
		this.price = entity.getPrice();
		this.typeCd = Optional.ofNullable(entity.getTypeCd())
				.map(AppDictionaryType.ORDER_TYPE_CD::lookupValue)
				.orElse(null);
		this.statusCd = Optional.ofNullable(entity.getStatusCd())
				.map(AppDictionaryType.ORDER_STATUS_CD::lookupValue)
				.orElse(null);
		this.driverId = Optional.ofNullable(entity.getDriver())
				.map(Driver::getId)
				.orElse(null);
		this.driverFio = Optional.ofNullable(entity.getDriver())
				.map(Driver::getFio)
				.orElse(null);
		this.completionDate = entity.getCompletionDate();
		this.departurePoint = entity.getDeparturePoint();
		this.destinationPoint = entity.getDestinationPoint();
		this.comment = entity.getComment();
	}

}
