package com.example.truck.business.service;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.DriverDTO;
import com.example.truck.business.dto.TruckDTO;
import com.example.truck.business.dto.TruckDTO_;
import com.example.truck.business.repository.DriverRepository;
import com.example.truck.business.repository.TruckRepository;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.Dictionaries.TRUCK_STATUS_CD;
import com.example.truck.business.repository.dictionary.Dictionaries.TRUCK_TECHNICAL_CONDITION_CD;
import com.example.truck.business.repository.entity.Truck;
import com.example.truck.business.service.meta.TruckMetaBuilder;
import com.example.truck.business.service.util.CustomPostAction;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;

import static com.example.truck.business.controller.TeslerRestController.createTruckForm;
import static com.example.truck.business.controller.TeslerRestController.editTruckForm;

@Service
public class TruckService extends AbstractTeslerService<TruckDTO, Truck> {

	private final TruckRepository truckRepository;

	private final DriverRepository driverRepository;

	public TruckService(final TruckRepository truckRepository, final DriverRepository driverRepository) {
		super(TruckDTO.class, Truck.class, null, TruckMetaBuilder.class);
		this.truckRepository = truckRepository;
		this.driverRepository = driverRepository;
	}

	@Override
	public Actions<TruckDTO> getActions() {
		return Actions.<TruckDTO>builder()
				.create().withoutIcon().available(this::isNotSupervisor).add()
				.cancelCreate().withoutIcon().available(this::isNotSupervisor).add()
				.delete().withoutIcon().available(this::isNotSupervisor).add()
				.save().withoutIcon().available(this::isNotSupervisor).add()
				.build();
	}

	@Override
	protected CreateResult<TruckDTO> doCreateEntity(final Truck entity, final BusinessComponent bc) {
		entity.setStatusCd(TRUCK_STATUS_CD.FREE);
		entity.setTechnicalConditionCd(TRUCK_TECHNICAL_CONDITION_CD.SERVICEABLE);
		truckRepository.save(entity);
		return new CreateResult<>(entityToDto(bc, entity))
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.TRUCK_SCREEN_CREATE_TRUCK_VIEW,
								createTruckForm,
								entity.getId()
						)
				);
	}

	@Override
	protected ActionResultDTO<TruckDTO> doUpdateEntity(final Truck entity, final TruckDTO data, final BusinessComponent bc) {
		if (data.hasChangedFields()) {
			setIfChanged(data, TruckDTO_.model, entity::setModel);
			setIfChanged(data, TruckDTO_.carNumber, entity::setCarNumber);
			setMappedIfChanged(data, TruckDTO_.typeCd, entity::setTypeCd, AppDictionaryType.TRUCK_TYPE_CD::lookupName);
			setMappedIfChanged(
					data,
					TruckDTO_.technicalConditionCd,
					entity::setTechnicalConditionCd,
					AppDictionaryType.TRUCK_TECHNICAL_CONDITION_CD::lookupName
			);
			setMappedIfChanged(data, TruckDTO_.statusCd, entity::setStatusCd, AppDictionaryType.TRUCK_STATUS_CD::lookupName);
			setMappedIfChanged(data, TruckDTO_.cargoTypeCd, entity::setCargoTypeCd, AppDictionaryType.CARGO_TYPE_CD::lookupName);
			setIfChanged(data, TruckDTO_.dimensionWidth, entity::setDimensionWidth);
			setIfChanged(data, TruckDTO_.dimensionLength, entity::setDimensionLength);
			setIfChanged(data, TruckDTO_.loadCapacity, entity::setLoadCapacity);
			setDriverToTruck(data, entity);
		}
		return new ActionResultDTO<>(entityToDto(bc, entity))
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.TRUCK_SCREEN_EDIT_TRUCK_VIEW,
								editTruckForm,
								entity.getId()
						));
	}

	@Override
	public ActionResultDTO<DriverDTO> onCancel(final BusinessComponent bc) {
		return new ActionResultDTO<DriverDTO>()
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.TRUCK_SCREEN_TRUCK_LIST_VIEW
						)
				);
	}

	private void setDriverToTruck(final TruckDTO dto, final Truck entity) {
		if (dto.isFieldChanged(TruckDTO_.driverId)) {
			if (dto.getDriverId() == null) {
				entity.setDriver(null);
				entity.setStatusCd(TRUCK_STATUS_CD.FREE);
			} else {
				entity.setDriver(driverRepository.getById(dto.getDriverId()));
				entity.setStatusCd(TRUCK_STATUS_CD.BUSY);
			}
		}
	}

}
