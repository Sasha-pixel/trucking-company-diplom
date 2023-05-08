package com.example.truck.business.service.meta;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.DriverDTO;
import com.example.truck.business.dto.DriverDTO_;
import com.example.truck.business.dto.OrderIssueDTO_;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;

import static com.example.truck.business.controller.TeslerRestController.editDriverForm;
import static com.example.truck.business.service.util.DrillDownUtils.bcUrl;

@Service
public class DriverMetaBuilder extends AbstractTeslerMeta<DriverDTO> {

	@Override
	public void buildRowDependentMeta(final RowDependentFieldsMeta<DriverDTO> fields, final InnerBcDescription bcDescription, final Long rowId,
																		final Long parentId) {
		fields.setEnabled(DriverDTO_.fio, DriverDTO_.phone, DriverDTO_.email);
		fields.setRequired(DriverDTO_.fio, DriverDTO_.phone, DriverDTO_.email);

		fields.setDrilldown(
				OrderIssueDTO_.id,
				DrillDownType.INNER,
				bcUrl(ScreenViews.DRIVER_SCREEN_EDIT_DRIVER_VIEW, editDriverForm, rowId)
		);
	}

	@Override
	public void buildIndependentMeta(final FieldsMeta<DriverDTO> fields, final InnerBcDescription bcDescription, final Long parentId) {
		fields.setDictionaryTypeWithAllValues(DriverDTO_.statusCd, AppDictionaryType.DRIVER_STATUS_CD);
		fields.enableFilter(
				DriverDTO_.fio,
				DriverDTO_.phone,
				DriverDTO_.email,
				DriverDTO_.statusCd,
				DriverDTO_.truckModel
		);
		fields.setAllFilterValuesByLovType(DriverDTO_.statusCd, AppDictionaryType.DRIVER_STATUS_CD);
	}

}
