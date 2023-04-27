package com.example.truck.business.controller;

import lombok.Getter;

import org.springframework.stereotype.Component;

import com.example.truck.business.service.DriverService;
import com.example.truck.business.service.OrderService;
import io.tesler.core.crudma.bc.BcIdentifier;
import io.tesler.core.crudma.bc.EnumBcIdentifier;
import io.tesler.core.crudma.bc.impl.AbstractEnumBcSupplier;
import io.tesler.core.crudma.bc.impl.BcDescription;

/**
 * This is actually an analog of a usual @RestController.
 * When you add enum, you just add rest endpoints, that tesler UI can call.
 * We could actually make a usual @RestController and make it Generic,
 * but current enum approach shows, that it is less error-prone in huge enterprise projects
 * (because single line in this enum creates >5 rest endpoints)
 */
@Getter
public enum TeslerRestController implements EnumBcIdentifier {

	// @formatter:on

	orderList(OrderService.class),
		createOrderForm(OrderService.class),
		editOrderForm(orderList, OrderService.class),
			driverPickList(editOrderForm, DriverService.class);

	// @formatter:on

	public static final EnumBcIdentifier.Holder<TeslerRestController> Holder = new Holder<>(TeslerRestController.class);

	private final BcDescription bcDescription;

	TeslerRestController(final String parentName, final Class<?> serviceClass, final boolean refresh) {
		this.bcDescription = buildDescription(parentName, serviceClass, refresh);
	}

	TeslerRestController(final String parentName, final Class<?> serviceClass) {
		this(parentName, serviceClass, false);
	}

	TeslerRestController(final BcIdentifier parent, final Class<?> serviceClass, final boolean refresh) {
		this(parent == null ? null : parent.getName(), serviceClass, refresh);
	}

	TeslerRestController(final BcIdentifier parent, final Class<?> serviceClass) {
		this(parent, serviceClass, false);
	}

	TeslerRestController(final Class<?> serviceClass, final boolean refresh) {
		this((String) null, serviceClass, refresh);
	}

	TeslerRestController(final Class<?> serviceClass) {
		this((String) null, serviceClass, false);
	}

	@Component
	public static class BcSupplier extends AbstractEnumBcSupplier<TeslerRestController> {

		public BcSupplier() {
			super(TeslerRestController.Holder);
		}

	}

}
