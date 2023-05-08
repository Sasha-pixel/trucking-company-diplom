package com.example.truck.business.repository.dictionary;

import lombok.experimental.UtilityClass;

import io.tesler.api.data.dictionary.CoreDictionaries;
import io.tesler.api.data.dictionary.LOV;

@UtilityClass
public class Dictionaries extends CoreDictionaries {

	@UtilityClass
	public class INTERNAL_ROLE {

		public static final LOV ADMIN = new LOV("ADMIN");

		public static final LOV TESLER_USER = new LOV("TESLER_USER");

	}

	@UtilityClass
	public class ORDER_TYPE_CD {

		public static final LOV SINGLE = new LOV("SINGLE");

		public static final LOV SERIES = new LOV("SERIES");

	}

	@UtilityClass
	public class ORDER_STATUS_CD {

		public static final LOV DRAFT = new LOV("DRAFT");

		public static final LOV NEW = new LOV("NEW");

		public static final LOV BOOKED = new LOV("BOOKED");

		public static final LOV IN_PROCESS = new LOV("IN_PROCESS");

		public static final LOV COMPLETED = new LOV("COMPLETED");

		public static final LOV CANCELLED = new LOV("CANCELLED");

	}

	@UtilityClass
	public class DRIVER_STATUS_CD {

		public static final LOV FREE = new LOV("FREE");

		public static final LOV BUSY = new LOV("BUSY");

		public static final LOV FIRED = new LOV("FIRED");

		public static final LOV ON_HOLIDAY = new LOV("ON_HOLIDAY");

	}

	@UtilityClass
	public class TRUCK_STATUS_CD {

		public static final LOV FREE = new LOV("FREE");

		public static final LOV BUSY = new LOV("BUSY");

		public static final LOV FIRED = new LOV("FIRED");

		public static final LOV ON_SERVICE = new LOV("ON_SERVICE");

	}

	@UtilityClass
	public class TRUCK_TYPE_CD {

		public static final LOV VAN = new LOV("VAN");

		public static final LOV TRUCK = new LOV("TRUCK");

		public static final LOV LITTLE_TRUCK = new LOV("LITTLE_TRUCK");

	}

	@UtilityClass
	public class TRUCK_TECHNICAL_CONDITION_CD {

		public static final LOV SERVICEABLE = new LOV("SERVICEABLE");

		public static final LOV FAULTY = new LOV("FAULTY");

	}

	@UtilityClass
	public class CARGO_TYPE_CD {

		public static final LOV BULK = new LOV("BULK");

		public static final LOV LIQUID = new LOV("LIQUID");

		public static final LOV GAS = new LOV("GAS");

		public static final LOV OVERSIZE = new LOV("OVERSIZE");

		public static final LOV PRODUCTS = new LOV("PRODUCTS");

		public static final LOV SOLID = new LOV("SOLID");

		public static final LOV CARS = new LOV("CARS");

		public static final LOV FLAMMABLE_LIQUID = new LOV("FLAMMABLE_LIQUID");

		public static final LOV FLAMMABLE_GAS = new LOV("FLAMMABLE_GAS");

	}

}
