package com.example.truck.business.service;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.OrderIssueDto;
import com.example.truck.business.dto.OrderIssueDto_;
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
public class OrderService extends AbstractTeslerService<OrderIssueDto, OrderIssue> {

	private final OrderIssueRepository orderIssueRepository;

	public OrderService(final OrderIssueRepository orderIssueRepository) {
		super(OrderIssueDto.class, OrderIssue.class, null, OrderMetaBuilder.class);
		this.orderIssueRepository = orderIssueRepository;
	}

	@Override
	public Actions<OrderIssueDto> getActions() {
		return Actions.<OrderIssueDto>builder()
				.create().withoutIcon().add()
				.cancelCreate().withoutIcon().add()
				.delete().withoutIcon().add()
				.save().withoutIcon().add()
				.build();
	}

	@Override
	protected CreateResult<OrderIssueDto> doCreateEntity(final OrderIssue entity, final BusinessComponent bc) {
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
	protected ActionResultDTO<OrderIssueDto> doUpdateEntity(final OrderIssue entity, final OrderIssueDto data, final BusinessComponent bc) {
		if (data.hasChangedFields()) {
			setIfChanged(data, OrderIssueDto_.name, entity::setName);
			setMappedIfChanged(data, OrderIssueDto_.typeCd, entity::setTypeCd, AppDictionaryType.ORDER_TYPE_CD::lookupName);
			setMappedIfChanged(data, OrderIssueDto_.statusCd, entity::setTypeCd, AppDictionaryType.ORDER_STATUS_CD::lookupName);
			setIfChanged(data, OrderIssueDto_.completionDate, entity::setCompletionDate);
			setIfChanged(data, OrderIssueDto_.departurePoint, entity::setDeparturePoint);
			setIfChanged(data, OrderIssueDto_.destinationPoint, entity::setDestinationPoint);
			setIfChanged(data, OrderIssueDto_.comment, entity::setComment);
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
	public ActionResultDTO<OrderIssueDto> onCancel(final BusinessComponent bc) {
		return new ActionResultDTO<OrderIssueDto>()
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.ORDER_SCREEN_ORDER_LIST_VIEW
						)
				);
	}

}
