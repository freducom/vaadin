/*
 * Copyright 2012 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.tests.components.textfield;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.tests.components.TestBase;
import com.vaadin.tests.util.TestUtils;
import com.vaadin.ui.TextArea;
import com.vaadin.v7.ui.LegacyAbstractTextField;
import com.vaadin.v7.ui.LegacyField;
import com.vaadin.v7.ui.LegacyTextField;

public class TextChangeListenerLosesFocus extends TestBase {

    private final TextChangeListener listener = new TextChangeListener() {
        @Override
        public void textChange(TextChangeEvent event) {
            final String value = event.getText();
            if (value.length() > 2) {
                ((LegacyField) event.getComponent())
                        .setValue("Updated by TextChangeListener");
            }
        }
    };

    @Override
    protected void setup() {
        TestUtils.injectCSS(getMainWindow(),
                ".v-textfield-focus, .v-textarea-focus { "
                        + " background: #E8F0FF !important }");

        LegacyAbstractTextField field = new LegacyTextField();
        field.setDebugId("test-textfield");
        field.setInputPrompt("Enter at least 3 characters");
        field.addTextChangeListener(listener);
        addComponent(field);

        field = new TextArea();
        field.setDebugId("test-textarea");
        field.setInputPrompt("Enter at least 3 characters");
        field.addTextChangeListener(listener);
        addComponent(field);

    }

    @Override
    protected String getDescription() {
        return "Updating a focused TextField overwrites the focus stylename";
    }

    @Override
    protected Integer getTicketNumber() {
        return 11623;
    }
}
