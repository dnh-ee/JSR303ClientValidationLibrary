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
package com.yannart.validation.factory;

import java.util.*;

import com.yannart.validation.JSR349ConstraintConverter;
import com.yannart.validation.converter.MaxConverter;
import com.yannart.validation.converter.MinConverter;
import com.yannart.validation.converter.RequiredConverter;
import com.yannart.validation.converter.SizeConverter;
import org.reflections.Reflections;

/**
 * Factory used to obtain the ConstraintConverters usable for a JSR303
 * constraint.
 * 
 * @author Yann Nicolas
 */
public class ConstraintConverterFactory {

	/**
	 * Default converters.
	 */
	static List<JSR349ConstraintConverter> converters;

    static {
        Reflections reflections = new Reflections("com.yannart.validation.converter");

        Set<Class<? extends JSR349ConstraintConverter>> converterClasses = reflections.getSubTypesOf(JSR349ConstraintConverter.class);

        converters = new ArrayList<>();
        for( Class<? extends JSR349ConstraintConverter> converterClass : converterClasses) {
            converters.add( instantiateConverter( converterClass));
        }
    }

    private static JSR349ConstraintConverter instantiateConverter(Class<? extends JSR349ConstraintConverter> converterClass) {
        try {
            return converterClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException( "Cannot create converter class", e);
        }
    }

    /**
	 * Map that is used for performance purposes and that allows finding all the
	 * converters that can be used with a particular annotation.
	 */
	private Map<Class<?>, Set<JSR349ConstraintConverter>> validationConverterByAnnotationMap;

	/**
	 * Constructor.
	 */
	public ConstraintConverterFactory() {
		validationConverterByAnnotationMap = new HashMap<Class<?>, Set<JSR349ConstraintConverter>>();
		generateConverterMapByAnnotationClass();
	}

	/**
	 * Generates the map that allows finding all the converters that can be used
	 * with a particular annotation.
	 */
	protected void generateConverterMapByAnnotationClass() {

		for (JSR349ConstraintConverter converter : converters) {
            // Gets the existing set of converters or generating an new one
            Set<JSR349ConstraintConverter> convertersForClass = ensureMapEntry( converter.annotationClassConverted());
            convertersForClass.add(converter);
		}
	}

    private Set<JSR349ConstraintConverter> ensureMapEntry( Class<?> convertedClass) {
        Set<JSR349ConstraintConverter> result = validationConverterByAnnotationMap
                .get(convertedClass);
        if (result == null) {
            result = new HashSet<JSR349ConstraintConverter>();
            validationConverterByAnnotationMap.put(convertedClass, result);
        }
        return result;
    }

	/**
	 */
	public Set<JSR349ConstraintConverter> getConverterMapByAnnotationClass(
			final Class<?> annotationClass) {
		return validationConverterByAnnotationMap.get(annotationClass);
	}
}
