package com.example.truck.business.service;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.CargoDTO;
import com.example.truck.business.dto.CargoDTO_;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.entity.Cargo;
import com.example.truck.business.repository.entity.Cargo_;
import com.example.truck.business.service.meta.CargoMetaBuilder;
import com.example.truck.business.service.util.CustomPostAction;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;

import static com.example.truck.business.controller.TeslerRestController.editOrderForm;

@Service
public class CargoService extends AbstractTeslerService<CargoDTO, Cargo> {

	public CargoService() {
		super(CargoDTO.class, Cargo.class, Cargo_.orderIssue, CargoMetaBuilder.class);
	}

	@Override
	public Actions<CargoDTO> getActions() {
		return Actions.<CargoDTO>builder()
				.save().withoutIcon().add()
				.build();
	}

	@Override
	protected CreateResult<CargoDTO> doCreateEntity(final Cargo entity, final BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<CargoDTO> doUpdateEntity(final Cargo entity, final CargoDTO data, final BusinessComponent bc) {
		if (data.hasChangedFields()) {
			setIfChanged(data, CargoDTO_.name, entity::setName);
			setMappedIfChanged(data, CargoDTO_.typeCd, entity::setTypeCd, AppDictionaryType.CARGO_TYPE_CD::lookupName);
			setIfChanged(data, CargoDTO_.dimensionWidth, entity::setDimensionWidth);
			setIfChanged(data, CargoDTO_.dimensionLength, entity::setDimensionLength);
			setIfChanged(data, CargoDTO_.weight, entity::setWeight);
		}
		return new ActionResultDTO<>(entityToDto(bc, entity))
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.ORDER_SCREEN_EDIT_ORDER_VIEW,
								editOrderForm,
								bc.getParentIdAsLong()
						));
	}

}
