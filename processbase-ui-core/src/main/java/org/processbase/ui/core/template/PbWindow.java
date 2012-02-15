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

import com.vaadin.ui.Layout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import org.processbase.ui.core.ProcessbaseApplication;

/**
 *
 * @author mgubaidullin
 */
public class PbWindow extends Window {

    public boolean confirm = false;

    public PbWindow() {
    }

    

    public PbWindow(String caption) {
        super(caption);
        ((Layout) getContent()).setStyleName(Reindeer.LAYOUT_WHITE);
    }

    public void showError(String description) {
        showMessage(description, Notification.TYPE_ERROR_MESSAGE);
    }

    public void showInformation(String description) {
        showMessage(description, Notification.TYPE_HUMANIZED_MESSAGE);
    }

    public void showWarning(String description) {
        showMessage(description, Notification.TYPE_WARNING_MESSAGE);
    }

    public void showImportantInformation(String description) {
        StringBuffer desc = new StringBuffer();
        if (description != null) {
            int i = 0;
            for (; description.length() > i + 50; i = i + 50) {
                desc.append("<br/> " + description.substring(i, i + 50));
            }
            desc.append("<br/> " + description.substring(i));
        } else {
            desc.append("<br/> null");
        }
        showNotification(ProcessbaseApplication.getCurrent().getPbMessages().getString("informationCaption"), desc.substring(0), Notification.TYPE_ERROR_MESSAGE);

    }

    public void showMessage(String description, int type) {
        StringBuilder desc = new StringBuilder();
        if (description != null) {
            int i = 0;
            for (; description.length() > i + 50; i = i + 50) {
                desc.append("<br/> ").append(description.substring(i, i + 50));
            }
            desc.append("<br/> ").append(description.substring(i));
        } else {
            desc.append("<br/> null");
        }
        switch (type) {
            case Notification.TYPE_WARNING_MESSAGE:
                showNotification(ProcessbaseApplication.getCurrent().getPbMessages().getString("warningCaption"), desc.substring(0), type);
                break;
            case Notification.TYPE_HUMANIZED_MESSAGE:
                showNotification(ProcessbaseApplication.getCurrent().getPbMessages().getString("informationCaption"), desc.substring(0), type);
                break;
            case Notification.TYPE_ERROR_MESSAGE:
                showNotification(ProcessbaseApplication.getCurrent().getPbMessages().getString("exceptionCaption"), desc.substring(0), type);
                break;
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
