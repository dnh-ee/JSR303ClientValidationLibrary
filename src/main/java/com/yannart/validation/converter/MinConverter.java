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
package com.yannart.validation.converter;

import java.util.Map;

import javax.validation.constraints.Min;

import com.yannart.validation.model.ConstrainedProperty;
import com.yannart.validation.JSR349ConstraintConverter;

/**
 * Converter from the annotation <code>Min</code> to the attribute "min".
 * 
 * @author Yann Nicolas
 */
public class MinConverter implements JSR349ConstraintConverter<Min> {

	/**
	 * {@inheritDoc}
	 */
	public Class annotationClassConverted() {
		return Min.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public void fillConstrainedPropertyAttributes(final Min min,
			final Map<String, Object> attributes,
			final ConstrainedProperty validatedProperty) {

		validatedProperty.addAttribute("min", Long.toString(min.value()));
	}
}