package com.example.truck.infrastructure.deserialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.tesler.api.data.dictionary.LOV;

public class LovDeserializer extends JsonDeserializer<LOV> {

	@Override
	public LOV deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException {
		return new LOV(jsonParser.getValueAsString());
	}

}
