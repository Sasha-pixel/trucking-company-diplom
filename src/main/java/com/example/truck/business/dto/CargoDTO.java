package com.example.truck.business.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.DictionaryType;
import com.example.truck.business.repository.entity.Cargo;
import com.example.truck.business.repository.entity.OrderIssue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tesler.api.data.dto.DataResponseDTO;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CargoDTO extends DataResponseDTO {

	private String name;

	@DictionaryType(AppDictionaryType.CARGO_TYPE_CD)
	private String typeCd;

	private Double dimensionWidth;

	private Double dimensionLength;

	private Long weight;

	private Long orderId;

	public CargoDTO(final Cargo entity) {
		this.id = String.valueOf(entity.getId());
		this.name = entity.getName();
		this.typeCd = Optional.ofNullable(entity.getTypeCd())
				.map(AppDictionaryType.CARGO_TYPE_CD::lookupValue)
				.orElse(null);
		this.dimensionWidth = entity.getDimensionWidth();
		this.dimensionLength = entity.getDimensionLength();
		this.weight = entity.getWeight();
		this.orderId = Optional.ofNullable(entity.getOrderIssue())
				.map(OrderIssue::getId)
				.orElse(null);
	}

}
