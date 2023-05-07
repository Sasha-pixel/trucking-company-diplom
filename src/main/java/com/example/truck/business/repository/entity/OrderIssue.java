package com.example.truck.business.repository.entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.DictionaryType;
import com.example.truck.infrastructure.serializtion.IdBaseEntitySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.model.core.entity.BaseEntity;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDER_ISSUE")
public class OrderIssue extends BaseEntity {

	@Column(name = "NAME")
	private String name;

	@Column(name = "PRICE")
	private Long price;

	@Column(name = "ORDER_TYPE_CD")
	@DictionaryType(AppDictionaryType.ORDER_TYPE_CD)
	private LOV typeCd;

	@Column(name = "ORDER_STATUS_CD")
	@DictionaryType(AppDictionaryType.ORDER_STATUS_CD)
	private LOV statusCd;

	@ManyToOne
	@JoinColumn(name = "DRIVER_ID")
	@JsonSerialize(using = IdBaseEntitySerializer.class)
	private Driver driver;

	@Column(name = "COMPLETION_DATE")
	private LocalDateTime completionDate;

	@Column(name = "DEPARTURE_POINT")
	private String departurePoint;

	@Column(name = "DESTINATION_POINT")
	private String destinationPoint;

	@Column(name = "COMMENT")
	private String comment;

	@OneToOne(mappedBy = "orderIssue", cascade = CascadeType.ALL, orphanRemoval = true)
	private Cargo cargo;

}
