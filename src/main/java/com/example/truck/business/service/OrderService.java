package com.example.truck.business.service;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.OrderIssueDTO;
import com.example.truck.business.dto.OrderIssueDTO_;
import com.example.truck.business.repository.DriverRepository;
import com.example.truck.business.repository.OrderIssueRepository;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.Dictionaries.ORDER_STATUS_CD;
import com.example.truck.business.repository.entity.OrderIssue;
import com.example.truck.business.service.meta.OrderMetaBuilder;
import com.example.truck.business.service.util.CustomPostAction;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;

import static com.example.truck.business.controller.TeslerRestController.createOrderForm;
import static com.example.truck.business.controller.TeslerRestController.editOrderForm;

@Service
public class OrderService extends AbstractTeslerService<OrderIssueDTO, OrderIssue> {

	private final OrderIssueRepository orderIssueRepository;

	private final DriverRepository driverRepository;

	public OrderService(final OrderIssueRepository orderIssueRepository, final DriverRepository driverRepository) {
		super(OrderIssueDTO.class, OrderIssue.class, null, OrderMetaBuilder.class);
		this.orderIssueRepository = orderIssueRepository;
		this.driverRepository = driverRepository;
	}

	@Override
	public Actions<OrderIssueDTO> getActions() {
		return Actions.<OrderIssueDTO>builder()
				.create().withoutIcon().add()
				.cancelCreate().withoutIcon().add()
				.delete().withoutIcon().add()
				.save().withoutIcon().add()
				.build();
	}

	@Override
	protected CreateResult<OrderIssueDTO> doCreateEntity(final OrderIssue entity, final BusinessComponent bc) {
		entity.setStatusCd(ORDER_STATUS_CD.DRAFT);
		orderIssueRepository.save(entity);
		return new CreateResult<>(entityToDto(bc, entity))
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.ORDER_SCREEN_CREATE_ORDER_VIEW,
								createOrderForm,
								entity.getId()
						)
				);
	}

	@Override
	protected ActionResultDTO<OrderIssueDTO> doUpdateEntity(final OrderIssue entity, final OrderIssueDTO data, final BusinessComponent bc) {
		if (data.hasChangedFields()) {
			setIfChanged(data, OrderIssueDTO_.name, entity::setName);
			setMappedIfChanged(data, OrderIssueDTO_.typeCd, entity::setTypeCd, AppDictionaryType.ORDER_TYPE_CD::lookupName);
			setMappedIfChanged(data, OrderIssueDTO_.statusCd, entity::setTypeCd, AppDictionaryType.ORDER_STATUS_CD::lookupName);
			setIfChanged(data, OrderIssueDTO_.completionDate, entity::setCompletionDate);
			setIfChanged(data, OrderIssueDTO_.departurePoint, entity::setDeparturePoint);
			setIfChanged(data, OrderIssueDTO_.destinationPoint, entity::setDestinationPoint);
			setMappedIfChanged(data, OrderIssueDTO_.driverId, entity::setDriver, findEntity(driverRepository));
			setIfChanged(data, OrderIssueDTO_.comment, entity::setComment);
		}
		return new ActionResultDTO<>(entityToDto(bc, entity))
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.ORDER_SCREEN_EDIT_ORDER_VIEW,
								editOrderForm,
								entity.getId()
						));
	}

	@Override
	public ActionResultDTO<OrderIssueDTO> onCancel(final BusinessComponent bc) {
		return new ActionResultDTO<OrderIssueDTO>()
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.ORDER_SCREEN_ORDER_LIST_VIEW
						)
				);
	}

}
