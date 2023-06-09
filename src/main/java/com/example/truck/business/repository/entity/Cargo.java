package com.example.truck.business.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "CARGO")
public class Cargo extends BaseEntity {

	@Column(name = "NAME")
	private String name;

	@Column(name = "CARGO_TYPE_CD")
	@DictionaryType(AppDictionaryType.CARGO_TYPE_CD)
	private LOV typeCd;

	@Column(name = "DIMENSION_WIDTH")
	private Double dimensionWidth;

	@Column(name = "DIMENSION_LENGTH")
	private Double dimensionLength;

	@Column(name = "WEIGHT")
	private Long weight;

	@OneToOne(mappedBy = "cargo")
	private OrderIssue orderIssue;

	public CargoBuilder copy() {
		return this.toBuilder()
				.orderIssue(null);
	}

}
