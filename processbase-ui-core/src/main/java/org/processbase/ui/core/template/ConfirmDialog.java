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

import com.vaadin.terminal.gwt.server.JsonPaintTarget;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class ConfirmDialog extends Window {

    private static final long serialVersionUID = -2363125714643244070L;

    public interface Factory {
        public ConfirmDialog create(String windowCaption, String message,
                String okTitle, String cancelTitle);
    }

    static final String DEFAULT_WINDOW_CAPTION = "Confirm";
    static final String DEFAULT_CANCEL_CAPTION = "Cancel";
    static final String DEFAULT_OK_CAPTION = "Ok";

    public static final int CONTENT_TEXT_WITH_NEWLINES = -1;
    public static final int CONTENT_TEXT = Label.CONTENT_TEXT;
    public static final int CONTENT_PREFORMATTED = Label.CONTENT_PREFORMATTED;
    public static final int CONTENT_HTML = Label.CONTENT_RAW;
    public static final int CONTENT_DEFAULT = CONTENT_TEXT_WITH_NEWLINES;

    public interface Listener {
        void onClose(ConfirmDialog dialog);
    }

    private static ConfirmDialog.Factory factoryInstance;


    public static ConfirmDialog.Factory getFactory() {
        if (factoryInstance == null) {
            factoryInstance = new DefaultConfirmDialogFactory();
        }
        return factoryInstance;
    }

    public static void setFactory(ConfirmDialog.Factory newFactory) {
        factoryInstance = newFactory;
    }

    public static ConfirmDialog show(final Window parentWindow,
            final Listener listener) {
        return show(parentWindow, null, null, null, null, listener);
    }

    public static ConfirmDialog show(final Window parentWindow,
            final String message, final Listener listener) {
        return show(parentWindow, null, message, null, null, listener);
    }

    public static ConfirmDialog show(final Window parentWindow,
            final String windowCaption, final String message,
            final String okCaption, final String cancelCaption,
            final Listener listener) {
        ConfirmDialog d = getFactory().create(windowCaption, message,
                okCaption, cancelCaption);
        d.show(parentWindow, listener, true);
        return d;
    }

    private Listener confirmListener = null;
    private boolean confirmed = false;
    private Label message = null;
    private Button okButton = null;
    private Button cancelButton = null;
    private String originalMessageText;
    private int contentMode = CONTENT_TEXT_WITH_NEWLINES;


    public void show(final Window parentWindow, final Listener listener,
            boolean modal) {
        confirmListener = listener;
        center();
        setModal(modal);
        parentWindow.addWindow(this);
    }


    public boolean isConfirmed() {
        return confirmed;
    }

    public Listener getListener() {
        return confirmListener;
    }

    protected void setOkButton(Button okButton) {
        this.okButton = okButton;
    }

    public Button getOkButton() {
        return okButton;
    }

    protected void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    protected void setMessageLabel(Label message) {
        this.message = message;
    }

    public void setMessage(String message) {
        originalMessageText = message;
        this.message
                .setValue(CONTENT_TEXT_WITH_NEWLINES == contentMode ? formatDialogMessage(message)
                        : message);
    }

    public String getMessage() {
        return originalMessageText;
    }

    public int getContentMode() {
        return contentMode;
    }

    public void setContentMode(int contentMode) {
        this.contentMode = contentMode;
        message
                .setContentMode(contentMode == CONTENT_TEXT_WITH_NEWLINES ? CONTENT_TEXT
                        : contentMode);
        message
                .setValue(contentMode == CONTENT_TEXT_WITH_NEWLINES ? formatDialogMessage(originalMessageText)
                        : originalMessageText);
    }

    protected String formatDialogMessage(final String text) {
        return JsonPaintTarget.escapeXML(text).replaceAll("\n", "<br />");
    }

    protected void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    protected void close() {
        super.close();
    }


}
