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
package org.processbase.ui.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.terminal.gwt.server.PortletRequestListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.portlet.PortletConfig;
import javax.portlet.PortletSession;
import org.processbase.ui.bam.panel.BAMConfigurationPanel;
import org.processbase.ui.bam.panel.BPMMonitoringPanel;
import org.processbase.ui.bpm.panel.BPMConfigurationPanel;
import org.processbase.ui.bpm.panel.TaskListPanel;
import org.processbase.ui.bpm.panel.IdentityPanel;
import org.processbase.ui.core.BPMModule;
import org.processbase.ui.core.Constants;
import org.processbase.ui.core.ProcessbaseApplication;
import org.processbase.ui.core.template.PbPanel;
import org.processbase.ui.core.template.PbWindow;
import org.processbase.ui.osgi.PbPanelModuleService;

/**
 *
 * @author mgubaidullin
 */
public class PbPortlet extends ProcessbaseApplication implements PortletRequestListener {

    PbWindow mainWindow;
    PortletApplicationContext2 portletApplicationContext2 = null;
    PortletSession portletSession = null;
    BPMModule bpmModule = null;
    ResourceBundle messages = null;
    String userName = null;
    Locale locale = null;
    int type = LIFERAY_PORTAL;
    private boolean inited = false;
    private String portletId = null;
    private boolean control = false;

    public void initUI() {
//        System.out.println("PbPortlet init ");
        setTheme("processbase");
        setLogoutURL(Constants.TASKLIST_PAGE_URL);
        setPortletApplicationContext2((PortletApplicationContext2) getContext());
        PortletConfig config = getPortletApplicationContext2().getPortletConfig();

        mainWindow = new PbWindow("Processbase BPMS Console Portlet");
        mainWindow.setSizeFull();
        setMainWindow(mainWindow);
        PbPanel ui = null;
        if (config.getInitParameter("ui").equalsIgnoreCase("ConsolePanel")) {
            ui = new TaskListPanel();
        } else if (config.getInitParameter("ui").equalsIgnoreCase("AdminPanel")) {
            ui = new BPMConfigurationPanel();
        } else if (config.getInitParameter("ui").equalsIgnoreCase("IdentityPanel")) {
            ui = new IdentityPanel();
        } else if (config.getInitParameter("ui").equalsIgnoreCase("BAMPanel")) {
            ui = new BAMConfigurationPanel();
        } else if (config.getInitParameter("ui").equalsIgnoreCase("MonitoringPanel")) {
            ui = new BPMMonitoringPanel();
        }
        ui.setControl(control);
        mainWindow.setContent(ui);
        ui.initUI();

    }

    public void onRequestStart(PortletRequest request, PortletResponse response) {
//        System.out.println("PbPortlet onRequestStart ");
        portletId = PortalUtil.getPortletId(request);
        Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
        if (portlet != null && portlet.getControlPanelEntryCategory() != null && portlet.getControlPanelEntryCategory().equals("portal")) {
            control = true;
        }

        if (!inited) {
            try {
                User user = PortalUtil.getUser(request);
                setUserName(user.getScreenName());
                setLocale(request.getLocale());
                setMessages(ResourceBundle.getBundle("resources/MessagesBundle", getLocale()));
                setBpmModule(new BPMModule(user.getScreenName()));
                setPortletSession(request.getPortletSession());
                inited = true;
            } catch (PortalException e) {
                e.printStackTrace();
            } catch (SystemException e) {
                e.printStackTrace();
            }
        }
    }

    public void onRequestEnd(PortletRequest request, PortletResponse response) {
    }

//    /**
//     * @return the current application instance
//     */
//    public static Processbase getCurrent() {
//        return currentProcessbase.get();
//    }
//
//    /**
//     * Set the current application instance
//     */
//    public static void setCurrent(Processbase application) {
//        if (getCurrent() == null) {
//            currentProcessbase.set(application);
//        }
//    }
//
//    /**
//     * Remove the current application instance
//     */
//    public static void removeCurrent() {
//        currentProcessbase.remove();
//    }
    public void setSessionAttribute(String name, String value) {
        getPortletSession().setAttribute("PROCESSBASE_SHARED_" + name, value, PortletSession.APPLICATION_SCOPE);
    }

    public void removeSessionAttribute(String name) {
        getPortletSession().removeAttribute("PROCESSBASE_SHARED_" + name, PortletSession.APPLICATION_SCOPE);
    }

    public Object getSessionAttribute(String name) {
        return getPortletSession().getAttribute("PROCESSBASE_SHARED_" + name, PortletSession.APPLICATION_SCOPE);

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BPMModule getBpmModule() {
        return bpmModule;
    }

    public void setBpmModule(BPMModule bpmModule) {
        this.bpmModule = bpmModule;
    }

    public ResourceBundle getPbMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

//    public User getPortalUser() {
//        return portalUser;
//    }
//
//    public void setPortalUser(User portalUser) {
//        this.portalUser = portalUser;
//    }
    public PortletSession getPortletSession() {
        return portletSession;
    }

    public void setPortletSession(PortletSession portletSession) {
        this.portletSession = portletSession;
    }

    public PortletApplicationContext2 getPortletApplicationContext2() {
        return portletApplicationContext2;
    }

    public void setPortletApplicationContext2(PortletApplicationContext2 portletApplicationContext2) {
        this.portletApplicationContext2 = portletApplicationContext2;
    }

    public int getApplicationType() {
        return ProcessbaseApplication.LIFERAY_PORTAL;
    }

    public PbPanelModuleService getPanelModuleService() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResourceBundle getCustomMessages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCustomMessages(ResourceBundle customMessages) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
