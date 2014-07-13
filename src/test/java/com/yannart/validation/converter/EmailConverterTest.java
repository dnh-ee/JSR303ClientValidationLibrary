package com.yannart.validation.converter;

import com.yannart.validation.model.ConstrainedProperty;
import org.hibernate.validator.constraints.Email;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EmailConverterTest {

    EmailConverter converter;

    @Before
    public void setUp() {
        converter = new EmailConverter();
    }

    /**
     * Test method for
     * {@link com.yannart.validation.converter.EmailConverter#annotationClassConverted()}
     * .
     */
    @Test
    public void testAnnotationClassConverted() {
        assertEquals( Email.class, converter.annotationClassConverted());
    }

    @Test
    public void testFillConstrainedPropertyAttributes() {
        ConstrainedProperty property = new ConstrainedProperty("property");
        Map<String, Object> attributeMap = new HashMap<>();

        Email annotation = mock( Email.class);

        converter.fillConstrainedPropertyAttributes( annotation,
                attributeMap, property);
        assertEquals( 1, property.getAttributeMap().size());
        assertEquals("true", property.getAttributeMap().get("email"));

    }
}