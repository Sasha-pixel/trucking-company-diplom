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
import io.tesler.core.util.filter.SearchParameter;
import io.tesler.core.util.filter.provider.impl.LovValueProvider;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TruckDTO extends DataResponseDTO {

	private Long driverId;

	private String driverFio;

	@SearchParameter
	private String carNumber;

	@SearchParameter
	private String model;

	@SearchParameter(provider = LovValueProvider.class)
	@DictionaryType(AppDictionaryType.TRUCK_TYPE_CD)
	private String typeCd;

	@SearchParameter(provider = LovValueProvider.class)
	@DictionaryType(AppDictionaryType.TRUCK_TECHNICAL_CONDITION_CD)
	private String technicalConditionCd;

	@SearchParameter(provider = LovValueProvider.class)
	@DictionaryType(AppDictionaryType.TRUCK_STATUS_CD)
	private String statusCd;

	@SearchParameter(provider = LovValueProvider.class)
	@DictionaryType(AppDictionaryType.CARGO_TYPE_CD)
	private String cargoTypeCd;

	private Double dimensionWidth;

	private Double dimensionLength;

	private Long loadCapacity;

	public TruckDTO(final Truck entity) {
		this.id = String.valueOf(entity.getId());
		this.driverId = Optional.ofNullable(entity.getDriver())
				.map(Driver::getId)
				.orElse(null);
		this.driverFio = Optional.ofNullable(entity.getDriver())
				.map(Driver::getFio)
				.orElse(null);
		this.model = entity.getModel();
		this.carNumber = entity.getCarNumber();
		this.typeCd = Optional.ofNullable(entity.getTypeCd())
				.map(AppDictionaryType.TRUCK_TYPE_CD::lookupValue)
				.orElse(null);
		this.technicalConditionCd = Optional.ofNullable(entity.getTechnicalConditionCd())
				.map(AppDictionaryType.TRUCK_TECHNICAL_CONDITION_CD::lookupValue)
				.orElse(null);
		this.statusCd = Optional.ofNullable(entity.getStatusCd())
				.map(AppDictionaryType.TRUCK_STATUS_CD::lookupValue)
				.orElse(null);
		this.cargoTypeCd = Optional.ofNullable(entity.getCargoTypeCd())
				.map(AppDictionaryType.CARGO_TYPE_CD::lookupValue)
				.orElse(null);
		this.dimensionWidth = entity.getDimensionWidth();
		this.dimensionLength = entity.getDimensionLength();
		this.loadCapacity = entity.getLoadCapacity();
	}

}
