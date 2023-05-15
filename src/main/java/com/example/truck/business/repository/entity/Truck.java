package com.example.truck.business.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.DictionaryType;
import com.example.truck.infrastructure.serialization.IdBaseEntitySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.model.core.entity.BaseEntity;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRUCK")
public class Truck extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "DRIVER_ID")
	@JsonSerialize(using = IdBaseEntitySerializer.class)
	private Driver driver;

	@Column(name = "CAR_NUMBER")
	private String carNumber;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "TRUCK_TYPE_CD")
	@DictionaryType(AppDictionaryType.TRUCK_TYPE_CD)
	private LOV typeCd;

	@Column(name = "TRUCK_TECHNICAL_CONDITION_CD")
	@DictionaryType(AppDictionaryType.TRUCK_TECHNICAL_CONDITION_CD)
	private LOV technicalConditionCd;

	@Column(name = "TRUCK_STATUS_CD")
	@DictionaryType(AppDictionaryType.TRUCK_STATUS_CD)
	private LOV statusCd;

	@Column(name = "CARGO_TYPE_CD")
	@DictionaryType(AppDictionaryType.CARGO_TYPE_CD)
	private LOV cargoTypeCd;

	@Column(name = "DIMENSION_WIDTH")
	private Double dimensionWidth;

	@Column(name = "DIMENSION_LENGTH")
	private Double dimensionLength;

	@Column(name = "LOAD_CAPACITY")
	private Long loadCapacity;

}
