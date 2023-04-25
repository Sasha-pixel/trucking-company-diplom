package com.example.truck.business.repository.dictionary;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.tesler.api.data.dictionary.BaseLov;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@BaseLov(type = AppDictionaryType.class)
public @interface DictionaryType {

	AppDictionaryType value();

}
