/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.tests.server.component.twincolselect;

import java.util.Arrays;

import org.junit.Test;

import com.vaadin.tests.design.DeclarativeTestBase;
import com.vaadin.ui.TwinColSelect;

/**
 * Test cases for reading the properties of selection components.
 *
 * @author Vaadin Ltd
 */
public class TwinColSelectDeclarativeTest
        extends DeclarativeTestBase<TwinColSelect> {

    public String getBasicDesign() {
        return "<vaadin-twin-col-select rows=5 right-column-caption='Selected values' left-column-caption='Unselected values'>\n"
                + "        <option>First item</option>\n"
                + "        <option selected>Second item</option>\n"
                + "        <option selected>Third item</option>\n"
                + "</vaadin-twin-col-select>";

    }

    public TwinColSelect getBasicExpected() {
        TwinColSelect s = new TwinColSelect();
        s.setRightColumnCaption("Selected values");
        s.setLeftColumnCaption("Unselected values");
        s.addItem("First item");
        s.addItem("Second item");
        s.addItem("Third item");
        s.setValue(Arrays.asList(new Object[] { "Second item", "Third item" }));
        s.setRows(5);
        return s;
    }

    @Test
    public void testReadBasic() {
        testRead(getBasicDesign(), getBasicExpected());
    }

    @Test
    public void testWriteBasic() {
        testWrite(stripOptionTags(getBasicDesign()), getBasicExpected());
    }

    @Test
    public void testReadEmpty() {
        testRead("<vaadin-twin-col-select />", new TwinColSelect());
    }

    @Test
    public void testWriteEmpty() {
        testWrite("<vaadin-twin-col-select />", new TwinColSelect());
    }

}