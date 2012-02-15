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
package org.processbase.ui.osgi;

import java.util.Locale;
import org.processbase.ui.core.template.PbPanel;

/**
 *
 * @author marat
 */
public abstract class PbPanelModule extends PbPanel {

    public PbPanelModule() {

    } 

    public String getName(){
        return this.getClass().getCanonicalName();
    }

    public abstract String getTitle(Locale locale);

//    public abstract PbPanel createComponent();

    protected void setModuleService(PbPanelModuleService service) {
        service.registerModule(this);
    }

    protected void unsetModuleService(PbPanelModuleService service) {
        service.unregisterModule(this);
    }
}
