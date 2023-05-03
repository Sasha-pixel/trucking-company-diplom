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

	}

	@UtilityClass
	public class DRIVER_STATUS_CD {

		public static final LOV FREE = new LOV("FREE");

		public static final LOV BUSY = new LOV("BUSY");

		public static final LOV FIRED = new LOV("FIRED");

		public static final LOV ON_HOLIDAY = new LOV("ON_HOLIDAY");

	}

}
