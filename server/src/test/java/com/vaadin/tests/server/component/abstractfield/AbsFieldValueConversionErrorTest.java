package com.vaadin.tests.server.component.abstractfield;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.data.util.MethodProperty;
import com.vaadin.tests.data.bean.Address;
import com.vaadin.tests.data.bean.Country;
import com.vaadin.tests.data.bean.Person;
import com.vaadin.tests.data.bean.Sex;
import com.vaadin.v7.data.Validator.InvalidValueException;
import com.vaadin.v7.data.util.converter.LegacyStringToIntegerConverter;
import com.vaadin.v7.data.util.converter.LegacyConverter.ConversionException;
import com.vaadin.v7.ui.LegacyTextField;

public class AbsFieldValueConversionErrorTest {

    Person paulaBean = new Person("Paula", "Brilliant", "paula@brilliant.com",
            34, Sex.FEMALE,
            new Address("Paula street 1", 12345, "P-town", Country.FINLAND));

    @Test
    public void testValidateConversionErrorParameters() {
        LegacyTextField tf = new LegacyTextField();
        tf.setConverter(new LegacyStringToIntegerConverter());
        tf.setPropertyDataSource(new MethodProperty<String>(paulaBean, "age"));
        tf.setConversionError("(Type: {0}) Converter exception message: {1}");
        tf.setValue("abc");
        try {
            tf.validate();
            fail();
        } catch (InvalidValueException e) {
            Assert.assertEquals(
                    "(Type: Integer) Converter exception message: Could not convert 'abc' to java.lang.Integer",
                    e.getMessage());
        }

    }

    @Test
    public void testConvertToModelConversionErrorParameters() {
        LegacyTextField tf = new LegacyTextField();
        tf.setConverter(new LegacyStringToIntegerConverter());
        tf.setPropertyDataSource(new MethodProperty<String>(paulaBean, "age"));
        tf.setConversionError("(Type: {0}) Converter exception message: {1}");
        tf.setValue("abc");
        try {
            tf.getConvertedValue();
            fail();
        } catch (ConversionException e) {
            Assert.assertEquals(
                    "(Type: Integer) Converter exception message: Could not convert 'abc' to java.lang.Integer",
                    e.getMessage());
        }

    }

    @Test
    public void testNullConversionMessages() {
        LegacyTextField tf = new LegacyTextField();
        tf.setConverter(new LegacyStringToIntegerConverter());
        tf.setPropertyDataSource(new MethodProperty<String>(paulaBean, "age"));
        tf.setConversionError(null);
        tf.setValue("abc");
        try {
            tf.validate();
            fail();
        } catch (InvalidValueException e) {
            Assert.assertEquals(null, e.getMessage());
        }

    }

    @Test
    public void testDefaultConversionErrorMessage() {
        LegacyTextField tf = new LegacyTextField();
        tf.setConverter(new LegacyStringToIntegerConverter());
        tf.setPropertyDataSource(new MethodProperty<String>(paulaBean, "age"));
        tf.setValue("abc");

        try {
            tf.validate();
            fail();
        } catch (InvalidValueException e) {
            Assert.assertEquals("Could not convert value to Integer",
                    e.getMessage());
        }

    }
}
