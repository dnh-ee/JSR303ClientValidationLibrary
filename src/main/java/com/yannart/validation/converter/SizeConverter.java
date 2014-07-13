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

import javax.validation.constraints.Size;

import com.yannart.validation.JSR349ConstraintConverter;
import com.yannart.validation.model.ConstrainedProperty;

/**
 * Converter from the annotation <code>Size</code> to the attributes "minlength"
 * and "maxlength".
 * 
 * @author Yann Nicolas
 */
public class SizeConverter implements JSR349ConstraintConverter<Size> {

	/**
	 * {@inheritDoc}
	 */
	public Class annotationClassConverted() {
		return Size.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public void fillConstrainedPropertyAttributes(final Size annotation,
			final Map<String, Object> attributes,
			final ConstrainedProperty validatedProperty) {

        if (attributes.containsKey("min")) {
            validatedProperty.addAttribute("minlength",
                    attributes.get("min").toString());
        }

        if (attributes.containsKey("max")) {
            validatedProperty.addAttribute("maxlength",
                    attributes.get("max").toString());
        }
	}
}