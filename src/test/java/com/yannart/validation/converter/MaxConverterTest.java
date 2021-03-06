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
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Max;

import org.junit.Before;
import org.junit.Test;

import com.yannart.validation.model.ConstrainedProperty;

/**
 * Test for MaxConverter.
 * 
 * @author Yann Nicolas
 */
public class MaxConverterTest {

	/**
	 * Tested object.
	 */
	MaxConverter converter;

	@Before
	public void setUp() {
		converter = new MaxConverter();
	}

	/**
	 * Test method for
	 * {@link com.yannart.validation.converter.MaxConverter#annotationClassConverted()}
	 * .
	 */
	@Test
	public void testAnnotationClassConverted() {
		assertEquals(Max.class,
                converter.annotationClassConverted());
	}

	/**
	 * Test method for
	 * {@link com.yannart.validation.converter.MaxConverter#fillConstrainedPropertyAttributes(java.lang.annotation.Annotation, java.util.Map, com.yannart.validation.model.ConstrainedProperty)}
	 * .
	 */
	@Test
	public void testFillConstrainedPropertyAttributes() {

		ConstrainedProperty property = new ConstrainedProperty("property");
		Map<String, Object> attributeMap = new HashMap<>();

		Max max = mock(Max.class);
		when(max.value()).thenReturn(10L);

		converter.fillConstrainedPropertyAttributes(max, attributeMap,
                property);
        assertEquals( 1, property.getAttributeMap().size());
        assertEquals("10", property.getAttributeMap().get("max"));
	}

}
