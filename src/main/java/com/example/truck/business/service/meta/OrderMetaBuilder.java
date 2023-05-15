package com.example.truck.business.service.meta;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.OrderIssueDTO;
import com.example.truck.business.dto.OrderIssueDTO_;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.repository.dictionary.Dictionaries.INTERNAL_ROLE;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;

import static com.example.truck.business.controller.TeslerRestController.editOrderForm;
import static com.example.truck.business.controller.TeslerRestController.orderArchiveInfo;
import static com.example.truck.business.controller.TeslerRestController.orderArchiveList;
import static com.example.truck.business.controller.TeslerRestController.orderList;
import static com.example.truck.business.service.util.DrillDownUtils.bcUrl;

@Service
public class OrderMetaBuilder extends AbstractTeslerMeta<OrderIssueDTO> {

	@Override
	public void buildRowDependentMeta(final RowDependentFieldsMeta<OrderIssueDTO> fields, final InnerBcDescription bcDescription, final Long rowId,
																		final Long parentId) {
		fields.setEnabled(
				OrderIssueDTO_.name,
				OrderIssueDTO_.typeCd,
				OrderIssueDTO_.completionDate,
				OrderIssueDTO_.departurePoint,
				OrderIssueDTO_.destinationPoint,
				OrderIssueDTO_.driverId,
				OrderIssueDTO_.driverFio,
				OrderIssueDTO_.comment
		);
		fields.setRequired(
				OrderIssueDTO_.name,
				OrderIssueDTO_.typeCd,
				OrderIssueDTO_.completionDate,
				OrderIssueDTO_.departurePoint,
				OrderIssueDTO_.destinationPoint
		);
		if (currentUserRole(INTERNAL_ROLE.SUPERVISOR)) {
			fields.disableFields();
		}
		if (orderList.isBc(bcDescription)) {
			fields.setDrilldown(
					OrderIssueDTO_.id,
					DrillDownType.INNER,
					bcUrl(ScreenViews.ORDER_SCREEN_EDIT_ORDER_VIEW, editOrderForm, rowId)
			);
		} else if (orderArchiveList.isBc(bcDescription)) {
				fields.setDrilldown(
						OrderIssueDTO_.id,
						DrillDownType.INNER,
						bcUrl(ScreenViews.ORDER_SCREEN_ORDER_ARCHIVE_INFO_VIEW, orderArchiveInfo, rowId)
				);
		}
	}

	@Override
	public void buildIndependentMeta(final FieldsMeta<OrderIssueDTO> fields, final InnerBcDescription bcDescription, final Long parentId) {
		fields.setDictionaryTypeWithAllValues(OrderIssueDTO_.typeCd, AppDictionaryType.ORDER_TYPE_CD);
		fields.setDictionaryTypeWithAllValues(OrderIssueDTO_.statusCd, AppDictionaryType.ORDER_STATUS_CD);
		fields.enableFilter(
				OrderIssueDTO_.name,
				OrderIssueDTO_.typeCd,
				OrderIssueDTO_.statusCd,
				OrderIssueDTO_.driverFio
		);
		fields.setAllFilterValuesByLovType(OrderIssueDTO_.typeCd, AppDictionaryType.ORDER_TYPE_CD);
		fields.setAllFilterValuesByLovType(OrderIssueDTO_.statusCd, AppDictionaryType.ORDER_STATUS_CD);
	}

}
