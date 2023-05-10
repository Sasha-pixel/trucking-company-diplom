package com.example.truck.business.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.truck.business.dto.OrderIssueDTO;
import com.example.truck.business.dto.OrderIssueDTO_;
import com.example.truck.business.repository.CargoRepository;
import com.example.truck.business.repository.DriverRepository;
import com.example.truck.business.repository.OrderIssueRepository;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.Dictionaries.ORDER_STATUS_CD;
import com.example.truck.business.repository.entity.Cargo;
import com.example.truck.business.repository.entity.OrderIssue;
import com.example.truck.business.service.meta.OrderMetaBuilder;
import com.example.truck.business.service.util.CustomPostAction;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.dto.rowmeta.PostAction;
import io.tesler.core.service.action.Actions;

import static com.example.truck.business.controller.TeslerRestController.createOrderForm;
import static com.example.truck.business.controller.TeslerRestController.editOrderForm;
import static com.example.truck.business.controller.TeslerRestController.orderArchiveList;
import static com.example.truck.business.controller.TeslerRestController.orderList;

@Service
public class OrderIssueService extends AbstractTeslerService<OrderIssueDTO, OrderIssue> {

	private final OrderIssueRepository orderIssueRepository;

	private final DriverRepository driverRepository;

	private final CargoRepository cargoRepository;

	private final List<LOV> CANCELABLE_ORDER_STATUSES = List.of(ORDER_STATUS_CD.DRAFT, ORDER_STATUS_CD.BOOKED, ORDER_STATUS_CD.NEW);

	public OrderIssueService(
			final OrderIssueRepository orderIssueRepository,
			final DriverRepository driverRepository,
			final CargoRepository cargoRepository
	) {
		super(OrderIssueDTO.class, OrderIssue.class, null, OrderMetaBuilder.class);
		this.orderIssueRepository = orderIssueRepository;
		this.driverRepository = driverRepository;
		this.cargoRepository = cargoRepository;
	}

	@Override
	public Actions<OrderIssueDTO> getActions() {
		return Actions.<OrderIssueDTO>builder()
				.create().withoutIcon().add()
				.cancelCreate().withoutIcon().add()
				.save().withoutIcon().add()
				.action("cancel-order", "Отменить").withoutIcon()
				.available(this::isOrderCancellationAvailable).invoker(this::cancelOrder)
				.add()
				.action("copy-order", "Копировать").withoutIcon()
				.invoker(this::copyOrder)
				.add()
				.action("publish-order", "Опубликовать").withoutIcon()
				.available(this::isOrderInDraftStatus).invoker(this::publishOrder)
				.add()
				.build();
	}

	@Override
	protected CreateResult<OrderIssueDTO> doCreateEntity(final OrderIssue entity, final BusinessComponent bc) {
		entity.setStatusCd(ORDER_STATUS_CD.DRAFT);
		final Cargo cargo = new Cargo();
		cargo.setOrderIssue(entity);
		cargoRepository.save(cargo);
		entity.setCargo(cargo);
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

	@Override
	protected Specification<OrderIssue> getSpecification(final BusinessComponent bc) {
		if (orderList.isBc(bc)) {
			return OrderIssueRepository.findAllNonCancelledOrCompletedOrders();
		}
		if (orderArchiveList.isBc(bc)) {
			return OrderIssueRepository.findAllCancelledOrCompletedOrders();
		}
		return super.getSpecification(bc);
	}

	private boolean isOrderCancellationAvailable(final BusinessComponent bc) {
		return Optional.ofNullable(bc.getIdAsLong())
				.map(orderIssueRepository::getById)
				.map(OrderIssue::getStatusCd)
				.map(CANCELABLE_ORDER_STATUSES::contains)
				.orElse(false);
	}

	private boolean isOrderInDraftStatus(final BusinessComponent bc) {
		return Optional.ofNullable(bc.getIdAsLong())
				.map(orderIssueRepository::getById)
				.map(OrderIssue::getStatusCd)
				.map(ORDER_STATUS_CD.DRAFT::equals)
				.orElse(false);
	}

	private ActionResultDTO<OrderIssueDTO> cancelOrder(final BusinessComponent bc, final OrderIssueDTO dto) {
		orderIssueRepository.getById(bc.getIdAsLong()).setStatusCd(ORDER_STATUS_CD.CANCELLED);
		return new ActionResultDTO<OrderIssueDTO>().setAction(PostAction.refreshBc(bc));
	}

	private ActionResultDTO<OrderIssueDTO> copyOrder(final BusinessComponent bc, final OrderIssueDTO dto) {
		final OrderIssue copy = orderIssueRepository.save(
				orderIssueRepository.getById(bc.getIdAsLong())
						.copy()
						.build()
		);
		return new ActionResultDTO<>(entityToDto(bc, copy))
				.setAction(
						CustomPostAction.innerDrillDown(
								ScreenViews.ORDER_SCREEN_EDIT_ORDER_VIEW,
								editOrderForm,
								copy.getId()
						));
	}

	private ActionResultDTO<OrderIssueDTO> publishOrder(final BusinessComponent bc, final OrderIssueDTO dto) {
		orderIssueRepository.getById(bc.getIdAsLong()).setStatusCd(ORDER_STATUS_CD.NEW);
		return new ActionResultDTO<OrderIssueDTO>().setAction(PostAction.refreshBc(bc));
	}

}
