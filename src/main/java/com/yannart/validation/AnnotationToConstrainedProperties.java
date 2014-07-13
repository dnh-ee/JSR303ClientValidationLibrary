/**
 * Copyright (C) 2011 Yann Nicolas <yannart@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yannart.validation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.yannart.validation.model.ConstrainedProperty;
import com.yannart.validation.factory.ConstraintConverterFactory;

/**
 * Convert a class annotated with JSR303 to a Set of constrained properties.
 * 
 * @author Yann Nicolas
 */
public class AnnotationToConstrainedProperties {

	/**
	 * Factory used to obtain the converter instances.
	 */
	private ConstraintConverterFactory converterFactory;

    /**
     * Generates a set of constrained properties from a Bean class annotated
     * with JRE303 annotations.
     *
     * @param clazz
     *            Class of the Bean.
     * @param validator
     *            Validator used to find the Bean constraints.
     * @return A set of constrained properties.
     */
	public Set<ConstrainedProperty> generateConstrainedProperties(
			final Class<?> clazz, final Validator validator) {

		return generateConstrainedProperties(clazz, validator, new String [] {});
	}

    /**
     * Generates a set of constrained properties from a Bean class annotated
     * with JRE303 annotations.
     *
     * @param clazz
     *            Class of the Bean.
     * @param validator
     *            Validator used to find the Bean constraints.
     * @param propertiesToIgnore
     *            Properties to ignore from the generation.
     * @return A set of constrained properties.
     */
	public Set<ConstrainedProperty> generateConstrainedProperties(
			final Class<?> clazz, final Validator validator,
			final String... propertiesToIgnore) {

		List<String> propertiesToIgnoreList = Arrays.asList(propertiesToIgnore);

		// Set that will contain the constraint properties of the class to
		// validate
		Set<ConstrainedProperty> propertySet = new TreeSet<ConstrainedProperty>();

		BeanDescriptor beanDescriptor = validator.getConstraintsForClass(clazz);

		Set<PropertyDescriptor> constrainedProperties = beanDescriptor
				.getConstrainedProperties();

		// For each property, convert from JSR303 to JQuery validator
		// constraints.
		for (PropertyDescriptor constrainedProperty : constrainedProperties) {

			String propertyName = constrainedProperty.getPropertyName();

			// only process the property if it is not in the ignore list
			if (!propertiesToIgnoreList.contains(propertyName)) {

				ConstrainedProperty property = new ConstrainedProperty(
						propertyName);

				Set<ConstraintDescriptor<?>> constraintDescriptors = constrainedProperty
						.getConstraintDescriptors();

				for (ConstraintDescriptor<?> constraintDescriptor : constraintDescriptors) {

					Annotation annotation = constraintDescriptor
							.getAnnotation();
					Map<String, Object> attributes = constraintDescriptor
							.getAttributes();

					// Get all the converters capable to trait the annotation
					Set<JSR349ConstraintConverter> converters = converterFactory
							.getConverterMapByAnnotationClass(annotation
									.annotationType());

					// If converters are usable, use it to fill attributes of
					// the
					// property
					if (converters != null) {
						for (JSR349ConstraintConverter converter : converters) {
							converter.fillConstrainedPropertyAttributes(
									annotation, attributes, property);
						}
					}
				}

				// A property is considered only if it has almost 1 attribute.
				if (property.getAttributeNumber() > 0) {
					propertySet.add(property);
				}
			}

		}

		return propertySet;
	}

    /**
     * Sets the Converter Factory to use when converting the data.
     *
     * @param converterFactory
     *            the converterFactory to set.
     */
	public void setConverterFactory(ConstraintConverterFactory converterFactory) {
		this.converterFactory = converterFactory;
	}

	/**
	 * Gets the converter factory.
	 * 
	 * @return the converterFactory
	 */
	public final ConstraintConverterFactory getConverterFactory() {
		return converterFactory;
	}
}
