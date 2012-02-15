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

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;

/**
 *
 * @author mgubaidullin
 */
public class TableExecButtonBar extends HorizontalLayout{

    public TableExecButtonBar() {
        super();
    }

    public void addButton(TableLinkButton teb) {
        this.addComponent(teb);
    }
    public void addButton(TableLinkButton teb, Alignment alignment) {
        this.addComponent(teb);
        this.setComponentAlignment(teb, alignment);
    }
}
