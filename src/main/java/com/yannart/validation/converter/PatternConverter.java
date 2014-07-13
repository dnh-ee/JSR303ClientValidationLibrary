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

import com.yannart.validation.JSR349ConstraintConverter;
import com.yannart.validation.model.ConstrainedProperty;

import javax.validation.constraints.Pattern;
import java.util.Map;

/**
 * Converter from the annotation <code>Pattern</code> to the attribute "regex".
 *
 * Requires additional method to be added to validator JS object - see <a href="http://stackoverflow.com/questions/280759/jquery-validate-how-to-add-a-rule-for-regular-expression-validation"></a>http://stackoverflow.com/questions/280759/jquery-validate-how-to-add-a-rule-for-regular-expression-validation</a>
 * $.validator.addMethod( "regex", function(value, element, regexp) {
 *      var re = new RegExp(regexp);
 *      return this.optional(element) || re.test(value);
 *      },
 *  "Please check your input."
 *  );
 */
public class PatternConverter implements JSR349ConstraintConverter<Pattern> {

	/**
	 * {@inheritDoc}
	 */
	public Class annotationClassConverted() {
		return Pattern.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public void fillConstrainedPropertyAttributes(final Pattern annotation,
			final Map<String, Object> attributes,
			final ConstrainedProperty validatedProperty) {

		validatedProperty.addAttribute("regex", annotation.regexp());
	}
}