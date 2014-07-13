package com.yannart.validation.converter;

import com.yannart.validation.model.ConstrainedProperty;
import org.hibernate.validator.constraints.Email;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatternConverterTest {

    PatternConverter converter;

    @Before
    public void setUp() {
        converter = new PatternConverter();
    }

    /**
     * Test method for
     * {@link PatternConverter#annotationClassConverted()}
     * .
     */
    @Test
    public void testAnnotationClassConverted() {
        assertEquals( Pattern.class, converter.annotationClassConverted());
    }

    @Test
    public void testFillConstrainedPropertyAttributes() {
        ConstrainedProperty property = new ConstrainedProperty("property");
        Map<String, Object> attributeMap = new HashMap<>();

        Pattern annotation = mock( Pattern.class);
        when( annotation.regexp()).thenReturn( "^[a-zA-Z'.\\\\s]{1,40}$");

        converter.fillConstrainedPropertyAttributes( annotation,
                attributeMap, property);
        assertEquals(1, property.getAttributeMap().size());
        assertEquals("^[a-zA-Z'.\\\\s]{1,40}$", property.getAttributeMap().get("regex"));

    }
}