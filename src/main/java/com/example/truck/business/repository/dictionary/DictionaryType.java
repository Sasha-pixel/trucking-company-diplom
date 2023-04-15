package com.example.truck.business.repository.dictionary;

import static io.tesler.api.data.dictionary.DictionaryCache.dictionary;

import io.tesler.api.data.dictionary.IDictionaryType;
import io.tesler.api.data.dictionary.LOV;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DictionaryType implements Serializable, IDictionaryType {
	;

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
