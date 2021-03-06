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
package com.vaadin.tests.server.component.datefield;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.tests.design.DeclarativeTestBase;
import com.vaadin.v7.ui.LegacyDateField;

/**
 * Tests the declarative support for implementations of {@link LegacyDateField}.
 *
 * @author Vaadin Ltd
 * @since 7.4
 */
public class LegacyDateFieldDeclarativeTest
        extends DeclarativeTestBase<LegacyDateField> {

    private static final String TAG_NAME = "com_vaadin_v7_ui-legacy-date-field";

    private String getYearResolutionDesign() {
        return "<" + TAG_NAME + " resolution='year' value='2020'/>";
    }

    private LegacyDateField getYearResolutionExpected() {
        LegacyDateField df = new LegacyDateField();
        df.setResolution(Resolution.YEAR);
        df.setValue(new Date(2020 - 1900, 1 - 1, 1));
        return df;
    }

    private String getTimezoneDesign() {
        String timeZone = new SimpleDateFormat("Z").format(new Date());
        return String.format(
                "<" + TAG_NAME
                        + " range-start=\"2014-05-05 00:00:00%1$s\" range-end=\"2014-06-05 00:00:00%1$s\" date-out-of-range-message=\"Please select a sensible date\" date-format=\"yyyy-MM-dd\" lenient show-iso-week-numbers parse-error-message=\"You are doing it wrong\" time-zone=\"GMT+05:00\" value=\"2014-05-15 00:00:00%1$s\"/>",
                timeZone);
    }

    private LegacyDateField getTimezoneExpected() {
        LegacyDateField df = new LegacyDateField();

        df.setRangeStart(new Date(2014 - 1900, 5 - 1, 5));
        df.setRangeEnd(new Date(2014 - 1900, 6 - 1, 5));
        df.setDateOutOfRangeMessage("Please select a sensible date");
        df.setResolution(Resolution.DAY);
        df.setDateFormat("yyyy-MM-dd");
        df.setLenient(true);
        df.setShowISOWeekNumbers(true);
        df.setParseErrorMessage("You are doing it wrong");
        df.setTimeZone(TimeZone.getTimeZone("GMT+5"));
        df.setValue(new Date(2014 - 1900, 5 - 1, 15));

        return df;
    }

    @Test
    public void readTimezone() {
        testRead(getTimezoneDesign(), getTimezoneExpected());
    }

    @Test
    public void writeTimezone() {
        testWrite(getTimezoneDesign(), getTimezoneExpected());
    }

    @Test
    public void readYearResolution() {
        testRead(getYearResolutionDesign(), getYearResolutionExpected());
    }

    @Test
    public void writeYearResolution() {
        // Writing is always done in full resolution..
        String timeZone = new SimpleDateFormat("Z")
                .format(new Date(2020 - 1900, 1 - 1, 1));
        testWrite(
                getYearResolutionDesign().replace("2020",
                        "2020-01-01 00:00:00" + timeZone),
                getYearResolutionExpected());
    }

    @Test
    public void testReadOnlyValue() {
        Date date = new Date(2020 - 1900, 1 - 1, 1);
        String timeZone = new SimpleDateFormat("Z").format(date);
        String design = "<" + TAG_NAME
                + " readonly resolution='year' value='2020-01-01 00:00:00"
                + timeZone + "'/>";
        LegacyDateField df = new LegacyDateField();
        df.setResolution(Resolution.YEAR);
        df.setValue(date);
        df.setReadOnly(true);

        testRead(design, df);
        testWrite(design, df);
    }

    @Override
    public LegacyDateField testRead(String design, LegacyDateField expected) {
        return super.testRead(
                "<html><head><meta charset='UTF-8' name='package-mapping' content='com_vaadin_v7_ui:com.vaadin.v7.ui'></head> "
                        + design + "</html>",
                expected);
    }
}
