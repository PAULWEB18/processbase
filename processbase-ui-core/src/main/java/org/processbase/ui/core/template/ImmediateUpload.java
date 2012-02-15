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

import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;
import org.ow2.bonita.facade.runtime.Document;
import org.processbase.ui.core.ProcessbaseApplication;
import org.processbase.ui.core.bonita.forms.Widget;

/**
 *
 * @author marat
 */
public class ImmediateUpload extends Panel
        implements Button.ClickListener,
        Upload.SucceededListener,
        Upload.FailedListener,
        Upload.StartedListener,
        Upload.ProgressListener,
        Upload.FinishedListener,
        Upload.Receiver {

    private ProgressIndicator progressIndicator = new ProgressIndicator();
    private HorizontalLayout progressLayout = new HorizontalLayout();
    private HorizontalLayout statusLayout = new HorizontalLayout();
    private HorizontalLayout downloadLayout = new HorizontalLayout();
    private Upload upload = new Upload("", (Upload.Receiver) this);
    private Button deleteBtn = new Button();
    private Button downloadBtn = new Button();
    private Button cancelBtn = new Button();
    private Label status = new Label();
//    private Label label = new Label();
    private String fileName;
    private String mtype;
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private ResourceBundle messages;
    private boolean contentNew = false;
    private Widget widget;
    private Document document;

    public ImmediateUpload(Widget widget, Document document, ResourceBundle messages) {
        this.messages = messages;
        this.widget = widget;
        this.document = document;
        
        ((VerticalLayout)getContent()).setSpacing(true);

        if (widget.getLabel() != null) {
            setCaption(widget.getLabel());
//            addComponent(label);
        }

        deleteBtn.setStyleName(Reindeer.BUTTON_LINK);
        deleteBtn.setDescription(messages.getString("btnDelete"));
        deleteBtn.setIcon(new ThemeResource("icons/cancel.png"));
        deleteBtn.addListener((Button.ClickListener) this);
        downloadLayout.addComponent(deleteBtn);
        downloadBtn.setCaption(document.getContentFileName()!=null?document.getContentFileName():"");
        downloadBtn.setStyleName(Reindeer.BUTTON_LINK);
        downloadBtn.addListener((Button.ClickListener) this);
        downloadLayout.addComponent(downloadBtn);
        downloadLayout.setSpacing(true);

        // Make uploading start immediately when file is selected
        upload.setImmediate(true);
        upload.setButtonCaption(document.getContentFileName()!=null ? document.getContentFileName() : widget.getLabel());
        upload.setStyleName(Reindeer.BUTTON_LINK);
        upload.addListener((Upload.StartedListener) this);
        upload.addListener((Upload.ProgressListener) this);
        upload.addListener((Upload.SucceededListener) this);
        upload.addListener((Upload.FailedListener) this);
        upload.addListener((Upload.FinishedListener) this);

        if (document.getContentSize() > 0) {
            addComponent(downloadLayout);
        } else {
            addComponent(upload);
        }
        statusLayout.addComponent(status);
        progressLayout.setSpacing(true);
        progressLayout.addComponent(progressIndicator);
        progressLayout.setComponentAlignment(progressIndicator, Alignment.MIDDLE_CENTER);
        cancelBtn = new Button(messages.getString("btnCancel"), this);
        cancelBtn.setStyleName("small");
        progressLayout.addComponent(cancelBtn);

//      this.setStyleName("black");
    }

    public void buttonClick(ClickEvent event) {
        try {
            if (event.getButton().equals(cancelBtn)) {
                upload.interruptUpload();
            } else if (event.getButton().equals(deleteBtn)) {
                contentNew = false;
                baos = new ByteArrayOutputStream();
                clear();
                addComponent(upload);
            } else if (event.getButton().equals(downloadBtn)) {
                ByteArraySource bas = null;
                if (contentNew) {
                    bas = new ByteArraySource(baos.toByteArray());
                } else {
                    fileName = document.getContentFileName();
                    byte[] body = ProcessbaseApplication.getCurrent().getBpmModule().getDocumentContent(document.getUUID());
                    System.out.println("download - " + fileName+ " " + document.getUUID() + " " + body.length);
                    bas = new ByteArraySource(body);
                }
                StreamResource streamResource = new StreamResource(bas, fileName, getApplication());
                streamResource.setCacheTime(50000); // no cache (<=0) does not work with IE8
                getWindow().getWindow().open(streamResource, "_new");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void uploadStarted(StartedEvent event) {
        // This method gets called immediatedly after upload is started
        progressIndicator.setValue(0f);
        progressIndicator.setPollingInterval(500);
        status.setCaption(messages.getString("labelUploading") + " \"" + event.getFilename() + "\"");
        clear();
        addComponent(statusLayout);
        addComponent(progressLayout);
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        // This method gets called several times during the update
        progressIndicator.setValue(new Float(readBytes / (float) contentLength));
    }

    public void uploadSucceeded(SucceededEvent event) {
        // This method gets called when the upload finished successfully
        downloadBtn.setCaption(event.getFilename());
        contentNew = true;
        clear();
        addComponent(downloadLayout);
    }

    public void uploadFailed(FailedEvent event) {
        // This method gets called when the upload failed
        status.setCaption(messages.getString("labelUploadingInterrupted"));
        clear();
        addComponent(upload);
    }

    public void uploadFinished(FinishedEvent event) {
        // This method gets called always when the upload finished,
        // either succeeding or failing
//        progressLayout.setVisible(false);
        deleteBtn.setVisible(true);
    }

    public OutputStream receiveUpload(String filename, String mimetype) {
        fileName = filename;
        mtype = mimetype;
        return baos;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mtype;
    }

    public byte[] getFileBody() {
        return baos.toByteArray();
    }

    private void clear() {
        removeAllComponents();
//        addComponent(label);
    }

    public boolean isContentNew() {
        return contentNew;
    }

    public Document getDocument() {
        return document;
    }    
}
