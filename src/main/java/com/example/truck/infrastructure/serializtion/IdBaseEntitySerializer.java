package com.example.truck.infrastructure.serializtion;

import java.io.IOException;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.tesler.model.core.entity.BaseEntity;

/**
 * Сериализатор для {@link Entity}, включающий только Id этой сущности
 * Необходим для тех полей основной {@link Entity}, которые являются связанными {@link Entity},
 * и при этом в связанных {@link Entity} присутствует поле-ссылка на основную {@link Entity}
 * (для корректной сериализации сущностей, которые для удобства доступа ссылаются циклически друг на друга, например, {@link OneToOne} сurrent-поля)
 */
public class IdBaseEntitySerializer extends JsonSerializer<BaseEntity> {

	@Override
	public void serialize(final BaseEntity value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeEndObject();
	}

}
