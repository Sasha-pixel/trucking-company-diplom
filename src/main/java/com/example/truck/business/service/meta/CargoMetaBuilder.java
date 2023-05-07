package com.example.truck.business.service.meta;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.CargoDTO;
import com.example.truck.business.dto.CargoDTO_;
import com.example.truck.business.repository.dictionary.AppDictionaryType;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;

@Service
public class CargoMetaBuilder extends AbstractTeslerMeta<CargoDTO> {

	@Override
	public void buildRowDependentMeta(final RowDependentFieldsMeta<CargoDTO> fields, final InnerBcDescription bcDescription, final Long id,
																		final Long parentId) {
		fields.setEnabled(
				CargoDTO_.name,
				CargoDTO_.typeCd,
				CargoDTO_.dimensionWidth,
				CargoDTO_.dimensionLength,
				CargoDTO_.weight
		);
		fields.setRequired(
				CargoDTO_.name,
				CargoDTO_.typeCd,
				CargoDTO_.dimensionWidth,
				CargoDTO_.dimensionLength,
				CargoDTO_.weight
		);
	}

	@Override
	public void buildIndependentMeta(final FieldsMeta<CargoDTO> fields, final InnerBcDescription bcDescription, final Long parentId) {
		fields.setDictionaryTypeWithAllValues(CargoDTO_.typeCd, AppDictionaryType.CARGO_TYPE_CD);
	}

}
