package com.example.truck.business.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.truck.business.dto.DriverDTO;
import com.example.truck.business.dto.DriverDTO_;
import com.example.truck.business.repository.DriverRepository;
import com.example.truck.business.repository.OrderIssueRepository;
import com.example.truck.business.repository.dictionary.Dictionaries.DRIVER_STATUS_CD;
import com.example.truck.business.repository.entity.Cargo;
import com.example.truck.business.repository.entity.Driver;
import com.example.truck.business.repository.entity.OrderIssue;
import com.example.truck.business.service.meta.DriverMetaBuilder;
import com.example.truck.business.service.util.CustomPostAction;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;

import static com.example.truck.business.controller.TeslerRestController.createDriverForm;
import static com.example.truck.business.controller.TeslerRestController.editDriverForm;
import static com.example.truck.business.controller.TeslerRestController.orderDriverPickList;
import static com.example.truck.business.controller.TeslerRestController.truckDriverPickList;

@Service
public class DriverService extends AbstractTeslerService<DriverDTO, Driver> {

	private final DriverRepository driverRepository;

	private final OrderIssueRepository orderIssueRepository;

	public DriverService(final DriverRepository driverRepository, final OrderIssueRepository orderIssueRepository) {
		super(DriverDTO.class, Driver.class, null, DriverMetaBuilder.class);
		this.driverRepository = driverRepository;
		this.orderIssueRepository = orderIssueRepository;
	}

	@Override
	public Actions<DriverDTO> getActions() {
		return Actions.<DriverDTO>builder()
				.create().withoutIcon().add()
				.cancelCreate().withoutIcon().add()
				.delete().withoutIcon().add()
				.save().withoutIcon().add()
				.build();
	}

	@Override
	protected CreateResult<DriverDTO> doCreateEntity(final Driver entity, final BusinessComponent bc) {
		entity.setStatusCd(DRIVER_STATUS_CD.FREE);
		driverRepository.save(entity);
		return new CreateResult<>(entityToDto(bc, entity))
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.DRIVER_SCREEN_CREATE_DRIVER_VIEW,
								createDriverForm,
								entity.getId()
						)
				);
	}

	@Override
	protected ActionResultDTO<DriverDTO> doUpdateEntity(final Driver entity, final DriverDTO data, final BusinessComponent bc) {
		if (data.hasChangedFields()) {
			setIfChanged(data, DriverDTO_.fio, entity::setFio);
			setIfChanged(data, DriverDTO_.phone, entity::setPhone);
			setIfChanged(data, DriverDTO_.email, entity::setEmail);
		}
		return new ActionResultDTO<>(entityToDto(bc, entity))
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.DRIVER_SCREEN_EDIT_DRIVER_VIEW,
								editDriverForm,
								entity.getId()
						));
	}

	@Override
	public ActionResultDTO<DriverDTO> onCancel(final BusinessComponent bc) {
		return new ActionResultDTO<DriverDTO>()
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.DRIVER_SCREEN_DRIVER_LIST_VIEW
						)
				);
	}

	@Override
	protected Specification<Driver> getParentSpecification(final BusinessComponent bc) {
		if (orderDriverPickList.isBc(bc)) {
			final OrderIssue order = orderIssueRepository.getById(bc.getParentIdAsLong());
			final LOV cargoType = Optional.ofNullable(order.getCargo()).map(Cargo::getTypeCd).orElse(null);
			final Double maxCargoDimensionWidth = Optional.ofNullable(order.getCargo()).map(Cargo::getDimensionWidth).orElse(null);
			final Double maxCargoDimensionLength = Optional.ofNullable(order.getCargo()).map(Cargo::getDimensionLength).orElse(null);
			final Long maxCargoWeight = Optional.ofNullable(order.getCargo()).map(Cargo::getWeight).orElse(null);
			return DriverRepository.findAllFreeDriversForOrder(cargoType, maxCargoDimensionWidth, maxCargoDimensionLength, maxCargoWeight);
		}
		if (truckDriverPickList.isBc(bc)) {
			return DriverRepository.findAllDriversForSettingTruck();
		}
		return super.getParentSpecification(bc);
	}

}
