package com.example.truck.business.service;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.DriverDTO;
import com.example.truck.business.dto.DriverDTO_;
import com.example.truck.business.repository.DriverRepository;
import com.example.truck.business.repository.dictionary.Dictionaries.DRIVER_STATUS_CD;
import com.example.truck.business.repository.entity.Driver;
import com.example.truck.business.service.meta.DriverMetaBuilder;
import com.example.truck.business.service.util.CustomPostAction;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;

import static com.example.truck.business.controller.TeslerRestController.createDriverForm;
import static com.example.truck.business.controller.TeslerRestController.editDriverForm;

@Service
public class DriverService extends AbstractTeslerService<DriverDTO, Driver> {

	private final DriverRepository driverRepository;

	public DriverService(final DriverRepository driverRepository) {
		super(DriverDTO.class, Driver.class, null, DriverMetaBuilder.class);
		this.driverRepository = driverRepository;
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

}
