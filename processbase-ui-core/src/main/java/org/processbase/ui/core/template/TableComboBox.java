/**
 * Copyright (C) 2011 PROCESSBASE Ltd.
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, see <http://www.gnu.org/licenses/>.
 */
package org.processbase.ui.core.template;

import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;

/**
 *
 * @author mgubaidullin
 */
public class TableComboBox extends ComboBox {

    private Object tableValue = null;

    public TableComboBox(Object tv) {
        super();
        tableValue = tv;
    }

    public Object getTableValue() {
        return tableValue;
    }

    public void setTableValue(Object tableValue) {
        this.tableValue = tableValue;
    }

    void setContainerDataSource(String[] fileTypes) {
        this.addContainerProperty("id", String.class, null);
        this.addContainerProperty("name", String.class, null);
        for (String ft : fileTypes) {
            Item item = this.addItem(ft);
            item.getItemProperty("id").setValue(ft);
            item.getItemProperty("name").setValue(ft);
        }
    }
}
