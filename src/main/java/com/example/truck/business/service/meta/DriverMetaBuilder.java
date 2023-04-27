package com.example.truck.business.service.meta;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.DriverDto;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;

@Service
public class DriverMetaBuilder extends AbstractTeslerMeta<DriverDto> {

	@Override
	public void buildRowDependentMeta(final RowDependentFieldsMeta<DriverDto> fields, final InnerBcDescription bcDescription, final Long id,
																		final Long parentId) {

	}

	@Override
	public void buildIndependentMeta(final FieldsMeta<DriverDto> fields, final InnerBcDescription bcDescription, final Long parentId) {

	}

}
