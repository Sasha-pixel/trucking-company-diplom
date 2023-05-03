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
public class DriverDTO extends DataResponseDTO {

	private String fio;

	private String phone;

	private String email;

	@DictionaryType(AppDictionaryType.DRIVER_STATUS_CD)
	private String statusCd;

	private Long truckId;

	private String truckModel;

	public DriverDTO(final Driver entity) {
		this.id = String.valueOf(entity.getId());
		this.fio = entity.getFio();
		this.phone = entity.getPhone();
		this.email = entity.getEmail();
		this.statusCd = Optional.ofNullable(entity.getStatusCd())
				.map(AppDictionaryType.DRIVER_STATUS_CD::lookupValue)
				.orElse(null);
		this.truckId = Optional.ofNullable(entity.getTruck())
				.map(Truck::getId)
				.orElse(null);
		this.truckModel = Optional.ofNullable(entity.getTruck())
				.map(Truck::getModel)
				.orElse(null);
	}

}
