package com.example.truck.business.repository.dictionary;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import io.tesler.api.data.dictionary.IDictionaryType;
import io.tesler.api.data.dictionary.LOV;

import static io.tesler.api.data.dictionary.DictionaryCache.dictionary;

@Getter
@RequiredArgsConstructor
public enum AppDictionaryType implements Serializable, IDictionaryType {
	DRIVER_STATUS_CD,
	TRUCK_TYPE_CD,
	TRUCK_TECHNICAL_CONDITION_CD,
	TRUCK_STATUS_CD,
	ORDER_TYPE_CD,
	ORDER_STATUS_CD,
	CARGO_TYPE_CD;

	@Override
	public String getName() {
		return name();
	}

	@Override
	public LOV lookupName(final String val) {
		return dictionary().lookupName(val, this);
	}

	@Override
	public String lookupValue(final LOV lov) {
		return dictionary().lookupValue(lov, this);
	}
}
