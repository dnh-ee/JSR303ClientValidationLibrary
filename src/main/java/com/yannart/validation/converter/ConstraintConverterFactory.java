package com.yannart.validation.converter;

import java.util.Set;

public interface ConstraintConverterFactory {

	/**
	 * Obtain a set of converters usable to convert the provided annotation.
	 * 
	 * @param annotationClass
	 *            class of the annotation for which supported converters are
	 *            searched.
	 * @return set of converters usable to convert the provided annotation.
	 */
	public abstract Set<JSR303ConstraintConverter> getConverterMapByAnnotationClass(
			final Class<?> annotationClass);

}