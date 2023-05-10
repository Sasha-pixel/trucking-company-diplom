package com.example.truck.business.dto.api;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.tesler.api.data.dictionary.LOV;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class GetOrdersResponseDto {

	private OrderDto order;

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonInclude(NON_NULL)
	public static class OrderDto {

		private Long id;

		private LocalDateTime createdDate;

		private String name;

		private Long price;

		private LOV typeCd;

		private LOV statusCd;

		private LocalDateTime completionDate;

		private String departurePoint;

		private String destinationPoint;

		private String comment;

		private CargoDto cargo;


		@Getter
		@Setter
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		@JsonInclude(NON_NULL)
		public static class CargoDto {

			private String name;

			private LOV typeCd;

			private Double dimensionWidth;

			private Double dimensionLength;

			private Long weight;

		}

	}

}
