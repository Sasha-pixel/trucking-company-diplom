package com.example.truck.business.service.meta;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.OrderIssueDto;
import com.example.truck.business.dto.OrderIssueDto_;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;

import static com.example.truck.business.controller.TeslerRestController.editOrderForm;
import static com.example.truck.business.service.util.DrillDownUtils.bcUrl;

@Service
public class OrderMetaBuilder extends AbstractTeslerMeta<OrderIssueDto> {

	@Override
	public void buildRowDependentMeta(final RowDependentFieldsMeta<OrderIssueDto> fields, final InnerBcDescription bcDescription, final Long rowId,
																		final Long parentId) {
		fields.setEnabled(
				OrderIssueDto_.name,
				OrderIssueDto_.typeCd,
				OrderIssueDto_.completionDate,
				OrderIssueDto_.departurePoint,
				OrderIssueDto_.destinationPoint,
				OrderIssueDto_.comment
		);
		fields.setRequired(
				OrderIssueDto_.name,
				OrderIssueDto_.typeCd,
				OrderIssueDto_.completionDate,
				OrderIssueDto_.departurePoint,
				OrderIssueDto_.destinationPoint
		);
		fields.setDrilldown(
				OrderIssueDto_.id,
				DrillDownType.INNER,
				bcUrl(ScreenViews.ORDER_SCREEN_EDIT_ORDER_VIEW, editOrderForm, rowId)
		);
	}

	@Override
	public void buildIndependentMeta(final FieldsMeta<OrderIssueDto> fields, final InnerBcDescription bcDescription, final Long parentId) {
		fields.setDictionaryTypeWithAllValues(OrderIssueDto_.typeCd, AppDictionaryType.ORDER_TYPE_CD);
		fields.setDictionaryTypeWithAllValues(OrderIssueDto_.statusCd, AppDictionaryType.ORDER_STATUS_CD);
	}

}
