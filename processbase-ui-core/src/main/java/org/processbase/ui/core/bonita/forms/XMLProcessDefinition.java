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
package org.processbase.ui.core.bonita.forms;

import java.util.HashMap;

/**
 *
 * @author marat
 */
public class XMLProcessDefinition {

    private String name;
    private String label;
    private HashMap<String, XMLTaskDefinition> tasks = new HashMap<String, XMLTaskDefinition>();
    private boolean skipEntryPageFlow = false;

    public XMLProcessDefinition(String name, String label) {
        this.name = name;
        this.label = label;
    }


    public HashMap<String, XMLTaskDefinition> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<String, XMLTaskDefinition> tasks) {
        this.tasks = tasks;
    }

    public void addTask(String stepName, XMLTaskDefinition task) {
        this.tasks.put(stepName, task);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSkipEntryPageFlow() {
        return skipEntryPageFlow;
    }

    public void setSkipEntryPageFlow(boolean skipEntryPageFlow) {
        this.skipEntryPageFlow = skipEntryPageFlow;
    }

}
