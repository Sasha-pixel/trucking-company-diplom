package com.example.truck.business.service.meta;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import io.tesler.api.data.dictionary.LOV;
import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import io.tesler.core.util.session.SessionService;

public abstract class AbstractTeslerMeta<T extends DataResponseDTO> extends FieldMetaBuilder<T> {

	@Autowired
	protected SessionService sessionService;

	protected final boolean currentUserRole(final LOV role) {
		return Objects.equals(sessionService.getSessionUserRole(), role);
	}

}
