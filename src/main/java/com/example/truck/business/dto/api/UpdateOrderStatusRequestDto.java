package com.example.truck.business.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.truck.infrastructure.deserialization.LovDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.tesler.api.data.dictionary.LOV;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOrderStatusRequestDto {

	private Long orderId;

	@JsonDeserialize(using = LovDeserializer.class)
	private LOV orderStatus;

}
