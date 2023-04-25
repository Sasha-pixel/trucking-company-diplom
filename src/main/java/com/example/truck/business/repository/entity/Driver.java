package com.example.truck.business.repository.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.DictionaryType;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.model.core.entity.BaseEntity;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DRIVER")
public class Driver extends BaseEntity {

	@Column(name = "FIO")
	private String fio;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "DRIVER_STATUS_CD")
	@DictionaryType(AppDictionaryType.DRIVER_STATUS_CD)
	private LOV statusCd;

	@OneToOne(mappedBy = "driver")
	private Truck truck;

	@OneToMany(mappedBy = "driver")
	private List<OrderIssue> orderIssues;

}
