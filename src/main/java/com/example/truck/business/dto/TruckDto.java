package com.example.truck.business.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.DictionaryType;
import com.example.truck.business.repository.entity.Driver;
import com.example.truck.business.repository.entity.Truck;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tesler.api.data.dto.DataResponseDTO;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TruckDto extends DataResponseDTO {

	private String driverFio;

	private String carNumber;

	private String model;

	@DictionaryType(AppDictionaryType.TRUCK_TYPE_CD)
	private String typeCd;

	@DictionaryType(AppDictionaryType.TRUCK_TECHNICAL_CONDITION_CD)
	private String technicalConditionCd;

	@DictionaryType(AppDictionaryType.TRUCK_STATUS_CD)
	private String statusCd;

	@DictionaryType(AppDictionaryType.TRANSPORTED_CARGO_TYPE_CD)
	private String transportedCargoTypeCd;

	private String dimension;

	public TruckDto(final Truck entity) {
		this.id = String.valueOf(entity.getId());
		this.driverFio = Optional.ofNullable(entity.getDriver())
				.map(Driver::getFio)
				.orElse(null);
		this.carNumber = entity.getCarNumber();
		this.typeCd = Optional.ofNullable(entity.getTypeCd())
				.map(AppDictionaryType.TRUCK_TYPE_CD::lookupValue)
				.orElse(null);
		this.technicalConditionCd = Optional.ofNullable(entity.getTypeCd())
				.map(AppDictionaryType.TRUCK_TECHNICAL_CONDITION_CD::lookupValue)
				.orElse(null);
		this.statusCd = Optional.ofNullable(entity.getStatusCd())
				.map(AppDictionaryType.TRUCK_STATUS_CD::lookupValue)
				.orElse(null);
		this.transportedCargoTypeCd = Optional.ofNullable(entity.getStatusCd())
				.map(AppDictionaryType.TRANSPORTED_CARGO_TYPE_CD::lookupValue)
				.orElse(null);
		this.dimension = entity.getDimension();
	}

}
