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
package com.vaadin.tests.minitutorials.v7_4;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.LegacyGrid;
import com.vaadin.ui.UI;

@Theme("valo")
public class ShowingInlineDataInGrid extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final LegacyGrid grid = new LegacyGrid();

        grid.addColumn("Name").setSortable(true);
        grid.addColumn("Score", Integer.class);

        grid.addRow("Alice", 15);
        grid.addRow("Bob", -7);
        grid.addRow("Carol", 8);
        grid.addRow("Dan", 0);
        grid.addRow("Eve", 20);

        grid.select(2);

        grid.setHeightByRows(grid.getContainerDataSource().size());
        grid.setHeightMode(HeightMode.ROW);

        setContent(grid);
    }
}
