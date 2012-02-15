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

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import java.util.ResourceBundle;

/**
 *
 * @author mgubaidullin
 */
public class PbTableFieldFactory implements TableFieldFactory {

    protected ResourceBundle messages = null;

    @Override
    public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        Field field = null;
        if (propertyId.toString().equals("url")) {
            field = new TextField();
            ((TextField) field).setInputPrompt("Type Task Page URL here");
            ((TextField) field).setWidth("300px");
            ((TextField) field).setNullRepresentation("");
        } else if (propertyId.toString().equals("mobileUiClass")) {
            field = new TextField();
            ((TextField) field).setInputPrompt("Type Mobile UI Form Class here");
            ((TextField) field).setWidth("300px");
            ((TextField) field).setNullRepresentation("");
        } else {
            return field;
        }
        return field;
    }
}

