package com.vaadin.tests.data.converter;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.v7.data.util.converter.LegacyConverter;
import com.vaadin.v7.data.util.converter.LegacyReverseConverter;
import com.vaadin.v7.data.util.converter.LegacyStringToByteConverter;
import com.vaadin.v7.data.util.converter.LegacyConverter.ConversionException;

public class StringToByteConverterTest {

    LegacyStringToByteConverter converter = new LegacyStringToByteConverter();
    LegacyConverter<Byte, String> reverseConverter = new LegacyReverseConverter<Byte, String>(
            converter);

    @Test
    public void testNullConversion() {
        Assert.assertEquals("Null value was converted incorrectly", null,
                converter.convertToModel(null, Byte.class, null));
    }

    @Test
    public void testReverseNullConversion() {
        Assert.assertEquals("Null value reversely was converted incorrectly",
                null,
                reverseConverter.convertToModel(null, String.class, null));
    }

    @Test
    public void testEmptyStringConversion() {
        Assert.assertEquals("Empty value was converted incorrectly", null,
                converter.convertToModel("", Byte.class, null));
    }

    @Test
    public void testValueConversion() {
        Assert.assertEquals("Byte value was converted incorrectly",
                Byte.valueOf((byte) 10),
                converter.convertToModel("10", Byte.class, null));
    }

    @Test
    public void testReverseValueConversion() {
        Assert.assertEquals("Byte value reversely was converted incorrectly",
                reverseConverter.convertToModel((byte) 10, String.class, null),
                "10");
    }

    @Test
    public void testExtremeByteValueConversion() {
        byte b = converter.convertToModel("127", Byte.class, null);
        Assert.assertEquals(Byte.MAX_VALUE, b);
        b = converter.convertToModel("-128", Byte.class, null);
        Assert.assertEquals("Min byte value was converted incorrectly",
                Byte.MIN_VALUE, b);
    }

    @Test
    public void testValueOutOfRange() {
        Double[] values = new Double[] { Byte.MAX_VALUE * 2.0,
                Byte.MIN_VALUE * 2.0, Long.MAX_VALUE * 2.0,
                Long.MIN_VALUE * 2.0 };

        boolean accepted = false;
        for (Number value : values) {
            try {
                converter.convertToModel(String.format("%.0f", value),
                        Byte.class, null);
                accepted = true;
            } catch (ConversionException expected) {
            }
        }
        Assert.assertFalse("Accepted value outside range of int", accepted);
    }
}
