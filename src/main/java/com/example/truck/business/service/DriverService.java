package com.example.truck.business.service;

import org.springframework.stereotype.Service;

import com.example.truck.business.dto.DriverDto;
import com.example.truck.business.repository.entity.Driver;
import com.example.truck.business.service.meta.DriverMetaBuilder;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;

@Service
public class DriverService extends AbstractTeslerService<DriverDto, Driver> {

	public DriverService() {
		super(DriverDto.class, Driver.class, null, DriverMetaBuilder.class);
	}

	@Override
	protected CreateResult<DriverDto> doCreateEntity(final Driver entity, final BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<DriverDto> doUpdateEntity(final Driver entity, final DriverDto data, final BusinessComponent bc) {
		return null;
	}

}
