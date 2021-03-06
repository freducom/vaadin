package com.vaadin.tests.components.datefield;

import java.util.Date;
import java.util.Locale;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.tests.components.TestBase;
import com.vaadin.v7.data.Validator;
import com.vaadin.v7.data.validator.LegacyAbstractValidator;
import com.vaadin.v7.ui.LegacyDateField;

public class RequiredInvalidDateField extends TestBase {

    @SuppressWarnings("deprecation")
    @Override
    protected void setup() {
        // StringLengthValidator textValidator = new StringLengthValidator(
        // "String length must be 2-4");
        // textValidator.setMinLength(2);
        // textValidator.setMaxLength(4);
        //
        // // not required
        // TextField tf1 = new TextField();
        // tf1.addValidator(textValidator);
        // tf1.setImmediate(true);
        // addComponent(tf1);
        //
        // // required
        // TextField tf2 = new TextField();
        // tf2.addValidator(textValidator);
        // tf2.setRequired(true);
        // tf2.setImmediate(true);
        // addComponent(tf2);

        Date date = new Date(2011 - 1900, 9 - 1, 1);

        Validator dateValidator = new LegacyAbstractValidator<Date>(
                "Day of month must be an even number") {

            @Override
            protected boolean isValidValue(Date value) {
                if (value == null) {
                    return true;
                }

                return (value.getDate() % 2 == 0);
            }

            @Override
            public Class getType() {
                return Date.class;
            }
        };

        // not required
        Property<Date> dateProperty1 = new ObjectProperty<Date>(date);
        LegacyDateField dateField1 = new LegacyDateField("Not required",
                dateProperty1);
        dateField1.setLocale(new Locale("fi", "FI"));
        dateField1.setResolution(LegacyDateField.RESOLUTION_DAY);
        dateField1.setId("_DF1");
        dateField1.setImmediate(true);
        dateField1.addValidator(dateValidator);
        addComponent(dateField1);

        // required
        Property<Date> dateProperty2 = new ObjectProperty<Date>(date);
        LegacyDateField dateField2 = new LegacyDateField("Required",
                dateProperty2);
        dateField2.setLocale(new Locale("fi", "FI"));
        dateField2.setResolution(LegacyDateField.RESOLUTION_DAY);
        dateField2.setId("_DF2");
        dateField2.setRequired(true);
        dateField2.setImmediate(true);
        dateField2.addValidator(dateValidator);
        addComponent(dateField2);
    }

    @Override
    protected String getDescription() {
        return "Error indicator should be shown for a DateField with an "
                + "invalid value, also when the field is required";
    }

    @Override
    protected Integer getTicketNumber() {
        return 6770;
    }

}
