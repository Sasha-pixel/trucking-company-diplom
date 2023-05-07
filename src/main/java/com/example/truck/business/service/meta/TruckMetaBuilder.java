package com.example.truck.business.service.meta;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.OrderIssueDTO_;
import com.example.truck.business.dto.TruckDTO;
import com.example.truck.business.dto.TruckDTO_;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;

import static com.example.truck.business.controller.TeslerRestController.editTruckForm;
import static com.example.truck.business.service.util.DrillDownUtils.bcUrl;

@Service
public class TruckMetaBuilder extends AbstractTeslerMeta<TruckDTO> {

	@Override
	public void buildRowDependentMeta(final RowDependentFieldsMeta<TruckDTO> fields, final InnerBcDescription bcDescription, final Long rowId,
																		final Long parentId) {
		fields.setEnabled(
				TruckDTO_.model,
				TruckDTO_.carNumber,
				TruckDTO_.typeCd,
				TruckDTO_.statusCd,
				TruckDTO_.technicalConditionCd,
				TruckDTO_.cargoTypeCd,
				TruckDTO_.dimensionWidth,
				TruckDTO_.dimensionLength,
				TruckDTO_.loadCapacity,
				TruckDTO_.driverId,
				TruckDTO_.driverFio
		);
		fields.setRequired(
				TruckDTO_.model,
				TruckDTO_.carNumber,
				TruckDTO_.typeCd,
				TruckDTO_.statusCd,
				TruckDTO_.technicalConditionCd,
				TruckDTO_.cargoTypeCd,
				TruckDTO_.dimensionWidth,
				TruckDTO_.dimensionLength,
				TruckDTO_.loadCapacity
		);

		fields.setDrilldown(
				OrderIssueDTO_.id,
				DrillDownType.INNER,
				bcUrl(ScreenViews.TRUCK_SCREEN_EDIT_TRUCK_VIEW, editTruckForm, rowId)
		);
	}

	@Override
	public void buildIndependentMeta(final FieldsMeta<TruckDTO> fields, final InnerBcDescription bcDescription, final Long parentId) {
		fields.setDictionaryTypeWithAllValues(TruckDTO_.typeCd, AppDictionaryType.TRUCK_TYPE_CD);
		fields.setDictionaryTypeWithAllValues(TruckDTO_.statusCd, AppDictionaryType.TRUCK_STATUS_CD);
		fields.setDictionaryTypeWithAllValues(TruckDTO_.technicalConditionCd, AppDictionaryType.TRUCK_TECHNICAL_CONDITION_CD);
		fields.setDictionaryTypeWithAllValues(TruckDTO_.cargoTypeCd, AppDictionaryType.CARGO_TYPE_CD);
	}

}
