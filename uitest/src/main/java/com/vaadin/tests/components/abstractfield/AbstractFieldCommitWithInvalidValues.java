package com.vaadin.tests.components.abstractfield;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.tests.components.TestBase;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.v7.data.validator.LegacyStringLengthValidator;
import com.vaadin.v7.ui.LegacyTextField;
import com.vaadin.ui.Notification;

public class AbstractFieldCommitWithInvalidValues extends TestBase {

    private LegacyTextField tf;

    @Override
    protected String getDescription() {
        return "Commiting a field with invalid values should throw an exception";
    }

    @Override
    protected Integer getTicketNumber() {
        return 2532;
    }

    @Override
    protected void setup() {
        tf = new LegacyTextField("A field, must contain 1-2 chars",
                new ObjectProperty<String>("a"));
        tf.addValidator(
                new LegacyStringLengthValidator("Invalid length", 1, 2, false));
        tf.setBuffered(true);
        tf.setRequired(true);

        Button b = new Button("Commit", new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    tf.commit();
                    if (tf.isValid()) {
                        getMainWindow().showNotification(
                                "OK! Form validated and no error was thrown",
                                Notification.TYPE_HUMANIZED_MESSAGE);
                    } else {
                        getMainWindow().showNotification(
                                "Form is invalid but no exception was thrown",
                                Notification.TYPE_ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    if (tf.isValid()) {
                        getMainWindow().showNotification(
                                "Form is valid but an exception was thrown",
                                Notification.TYPE_ERROR_MESSAGE);
                    } else {
                        getMainWindow().showNotification(
                                "OK! Error was thrown for an invalid input",
                                Notification.TYPE_HUMANIZED_MESSAGE);

                    }
                }
            }

        });

        addComponent(tf);
        addComponent(b);
    }
}
