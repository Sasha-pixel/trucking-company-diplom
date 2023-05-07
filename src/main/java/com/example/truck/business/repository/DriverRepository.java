package com.example.truck.business.repository;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.truck.business.repository.dictionary.Dictionaries.DRIVER_STATUS_CD;
import com.example.truck.business.repository.dictionary.Dictionaries.TRUCK_STATUS_CD;
import com.example.truck.business.repository.dictionary.Dictionaries.TRUCK_TECHNICAL_CONDITION_CD;
import com.example.truck.business.repository.entity.Driver;
import com.example.truck.business.repository.entity.Driver_;
import com.example.truck.business.repository.entity.Truck_;
import io.tesler.api.data.dictionary.LOV;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {

	static Specification<Driver> findAllFreeDriversForOrder(
			final LOV cargoType,
			final Double maxCargoDimensionWidth,
			final Double maxCargoDimensionLength,
			final Long maxCargoWeight
	) {
		return (root, cq, cb) -> cb.and(
				cb.equal(root.get(Driver_.statusCd), DRIVER_STATUS_CD.FREE),
				cb.equal(root.get(Driver_.truck).get(Truck_.statusCd), TRUCK_STATUS_CD.BUSY),
				cb.equal(root.get(Driver_.truck).get(Truck_.technicalConditionCd), TRUCK_TECHNICAL_CONDITION_CD.SERVICEABLE),
				cargoType != null ? cb.equal(root.get(Driver_.truck).get(Truck_.cargoTypeCd), cargoType) : cb.and(),
				maxCargoDimensionWidth != null ? cb.greaterThan(
						root.get(Driver_.truck).get(Truck_.dimensionWidth),
						maxCargoDimensionWidth
				) : cb.and(),
				maxCargoDimensionLength != null ? cb.greaterThan(
						root.get(Driver_.truck).get(Truck_.dimensionLength),
						maxCargoDimensionLength
				) : cb.and(),
				maxCargoWeight != null ? cb.greaterThan(
						root.get(Driver_.truck).get(Truck_.loadCapacity),
						maxCargoWeight
				) : cb.and()
		);
	}

	static Specification<Driver> findAllDriversForSettingTruck() {
		return (root, cq, cb) -> cb.and(
				cb.isNull(root.join(Driver_.truck, JoinType.LEFT)),
				root.get(Driver_.statusCd).in(DRIVER_STATUS_CD.BUSY, DRIVER_STATUS_CD.FIRED).not()
		);
	}

}
