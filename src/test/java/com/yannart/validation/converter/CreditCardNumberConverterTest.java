package com.yannart.validation.converter;

import com.yannart.validation.model.ConstrainedProperty;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CreditCardNumberConverterTest {

    CreditCardNumberConverter converter;

    @Before
    public void setUp() {
        converter = new CreditCardNumberConverter();
    }

    /**
     * Test method for
     * {@link EmailConverter#annotationClassConverted()}
     * .
     */
    @Test
    public void testAnnotationClassConverted() {
        assertEquals( CreditCardNumber.class, converter.annotationClassConverted());
    }

    @Test
    public void testFillConstrainedPropertyAttributes() {
        ConstrainedProperty property = new ConstrainedProperty("property");
        Map<String, Object> attributeMap = new HashMap<>();

        CreditCardNumber annotation = mock( CreditCardNumber.class);

        converter.fillConstrainedPropertyAttributes( annotation,
                attributeMap, property);
        assertEquals(1, property.getAttributeMap().size());
        assertEquals("true", property.getAttributeMap().get("creditcard"));

    }
}