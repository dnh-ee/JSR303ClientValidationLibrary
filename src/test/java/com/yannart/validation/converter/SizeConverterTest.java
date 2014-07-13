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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.junit.Before;
import org.junit.Test;

import com.yannart.validation.model.ConstrainedProperty;

/**
 * Test for SizeConverter.
 * 
 * @author Yann Nicolas
 */
public class SizeConverterTest {

	/**
	 * Tested object.
	 */
	SizeConverter converter;

	@Before
	public void setUp() {
		converter = new SizeConverter();
	}

	/**
	 * Test method for
	 * {@link com.yannart.validation.converter.SizeConverter#annotationClassConverted()}
	 * .
	 */
	@Test
	public void testAnnotationClassConverted() {
		assertEquals(Size.class,
                converter.annotationClassConverted());
	}

	/**
	 * Test method for
	 * {@link com.yannart.validation.converter.SizeConverter#fillConstrainedPropertyAttributes(java.lang.annotation.Annotation, java.util.Map, com.yannart.validation.model.ConstrainedProperty)}
	 * .
	 */
	@Test
	public void testFillConstrainedPropertyAttributes() {

		ConstrainedProperty property = new ConstrainedProperty("property");
		Map<String, Object> attributeMap = new HashMap<>();

		// Test with no attribute
        Size annotation = mock(Size.class);
        converter.fillConstrainedPropertyAttributes( annotation, attributeMap,
                property);
        assertEquals(0, property.getAttributeMap().size());
		assertNull(property.getAttributeMap().get("minlength"));
		assertNull(property.getAttributeMap().get("maxlength"));

		// Test just a Max attribute
		property.getAttributeMap().clear();
		attributeMap.clear();
        attributeMap.put("max", 123);

		converter.fillConstrainedPropertyAttributes(annotation, attributeMap,
                property);
        assertEquals(1, property.getAttributeMap().size());
		assertEquals("123", property.getAttributeMap().get("maxlength"));
		assertNull(property.getAttributeMap().get("minlength"));

		// Test just a Min attribute
		property.getAttributeMap().clear();
		attributeMap.clear();
		attributeMap.put("min", 1);

		converter.fillConstrainedPropertyAttributes(annotation, attributeMap,
                property);
        assertEquals(1, property.getAttributeMap().size());
		assertEquals("1", property.getAttributeMap().get("minlength"));
		assertNull(property.getAttributeMap().get("maxlength"));

		// Test with both Min and Max attributes
		property.getAttributeMap().clear();
		attributeMap.clear();
		attributeMap.put("min", 10);
		attributeMap.put("max", 20);

		converter.fillConstrainedPropertyAttributes(annotation, attributeMap,
                property);
        assertEquals(2, property.getAttributeMap().size());
		assertEquals("10", property.getAttributeMap().get("minlength"));
		assertEquals("20", property.getAttributeMap().get("maxlength"));
	}

}
